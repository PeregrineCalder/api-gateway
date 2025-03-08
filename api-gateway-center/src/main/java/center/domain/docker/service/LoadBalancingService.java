package center.domain.docker.service;

import center.domain.docker.model.aggregates.NginxConfig;
import center.domain.docker.model.vo.LocationVO;
import center.domain.docker.model.vo.UpstreamVO;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.domain.docker.service
 * @className: LoadBalancingService
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
@Service
public class LoadBalancingService extends AbstractLoadBalancing {

    @Value("${nginx.server_name}")
    private String nginx_server_name;

    @Override
    protected String createNginxConfigFile(NginxConfig nginxConfig) throws IOException {
        String nginxConfigContentStr = buildNginxConfig(nginxConfig.getUpstreamList(), nginxConfig.getLocationList());
        File file = new File("/data/nginx/nginx.conf");
        if (!file.exists()) {
            boolean success = file.createNewFile();
            if (success) {
                log.info("nginx.conf file created successfully.");
            } else {
                log.info("nginx.conf file already exists.");
            }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(nginxConfigContentStr);
        writer.close();
        return file.getAbsolutePath();
    }

    @Override
    protected void copyDockerFile(String applicationName, String containerFilePath, String localNginxPath) throws InterruptedException, IOException {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock").build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        try (TarArchiveInputStream tarStream = new TarArchiveInputStream(
                dockerClient.copyArchiveFromContainerCmd(applicationName,
                        containerFilePath).exec())) {
            unTar(tarStream, new File(localNginxPath));
        }
        dockerClient.close();
    }

    private static void unTar(TarArchiveInputStream tis, File destFile)
            throws IOException {
        TarArchiveEntry tarEntry;
        while ((tarEntry = tis.getNextTarEntry()) != null) {
            if (tarEntry.isDirectory()) {
                if (!destFile.exists()) {
                    destFile.mkdirs();
                }
            } else {
                FileOutputStream fos = new FileOutputStream(destFile);
                IOUtils.copy(tis, fos);
                fos.close();
            }
        }
        tis.close();
    }

    @Override
    protected void refreshNginxConfig(String nginxName) throws InterruptedException, IOException {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("unix:///var/run/docker.sock").build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config).build();

        String containerId = dockerClient.listContainersCmd()
                .withNameFilter(new ArrayList<>() {{
                    add(nginxName);
                }})
                .exec()
                .get(0)
                .getId();

        ExecCreateCmdResponse execCreateCmdResponse = dockerClient
                .execCreateCmd(containerId)
                .withCmd("nginx", "-s", "reload")
                .exec();

        dockerClient.execStartCmd(execCreateCmdResponse.getId())
                .exec(new ResultCallback.Adapter<>()).awaitCompletion();

        dockerClient.close();
    }

    private String buildNginxConfig(List<UpstreamVO> upstreamList, List<LocationVO> locationList) {
        String config = "\n" +
                "user  nginx;\n" +
                "worker_processes  auto;\n" +
                "\n" +
                "error_log  /var/log/nginx/error.log notice;\n" +
                "pid        /var/run/nginx.pid;\n" +
                "\n" +
                "\n" +
                "events {\n" +
                "    worker_connections  1024;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "http {\n" +
                "    include       /etc/nginx/mime.types;\n" +
                "    default_type  application/octet-stream;\n" +
                "\n" +
                "    log_format  main  '$remote_addr - $remote_user [$time_local] \"$request\" '\n" +
                "                      '$status $body_bytes_sent \"$http_referer\" '\n" +
                "                      '\"$http_user_agent\" \"$http_x_forwarded_for\"';\n" +
                "\n" +
                "    access_log  /var/log/nginx/access.log  main;\n" +
                "\n" +
                "    sendfile        on;\n" +
                "    #tcp_nopush     on;\n" +
                "\n" +
                "    keepalive_timeout  65;\n" +
                "\n" +
                "    #gzip  on;\n" +
                "\n" +
                "    include /etc/nginx/conf.d/*.conf;\n" +
                "\n" +
                "upstream_config_placeholder" +
                "\n" +
                "    server {\n" +
                "        listen  80;\n" +
                "\n" +
                "        server_name "+nginx_server_name+";\n" +
                "\n" +
                "        index index.html;\n" +
                "\n" +
                "        location / {\n" +
                "            proxy_pass http://192.168.1.102:9001;\n" +
                "        }\n" +
                "\n" +
                "location_config_placeholder" +
                "    }\n" +
                "}\n";

        StringBuilder upstreamStr = new StringBuilder();
        for (UpstreamVO upstream : upstreamList) {
            upstreamStr.append("\t").append("upstream").append(" ").append(upstream.getName()).append(" {\r\n");
            upstreamStr.append("\t").append("\t").append(upstream.getStrategy()).append("\r\n").append("\r\n");
            List<String> servers = upstream.getServers();
            for (String server : servers) {
                upstreamStr.append("\t").append("\t").append("server").append(" ").append(server).append("\r\n");
            }
            upstreamStr.append("\t").append("}").append("\r\n").append("\r\n");
        }

        StringBuilder locationStr = new StringBuilder();
        for (LocationVO location : locationList) {
            locationStr.append("\t").append("\t").append("location").append(" ").append(location.getName()).append(" {\r\n");
            locationStr.append("\t").append("\t").append("\t").append("proxy_pass").append(" ").append(location.getProxy_pass()).append("\r\n");
            locationStr.append("\t").append("\t").append("}").append("\r\n").append("\r\n");
        }

        config = config.replace("upstream_config_placeholder", upstreamStr.toString());
        config = config.replace("location_config_placeholder", locationStr.toString());
        return config;
    }

}
