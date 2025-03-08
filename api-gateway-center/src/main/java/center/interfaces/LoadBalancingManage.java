package center.interfaces;

import center.application.ILoadBalancingService;
import center.domain.docker.model.aggregates.NginxConfig;
import center.domain.docker.model.vo.LocationVO;
import center.domain.docker.model.vo.UpstreamVO;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.interfaces
 * @className: LoadBalancingManage
 * @author: Peregrine Calder
 * @version: 1.0
 */
@RestController
@RequestMapping("/wg/admin/load")
@Slf4j
public class LoadBalancingManage {

    @Resource
    private ILoadBalancingService loadBalancingService;

    @GetMapping(value = "copy", produces = "application/json;charset=utf-8")
    public void copy() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("docker", "cp", "/nginx.conf", "host:/api-gateway/api-gateway-center/doc/data/nginx/nginx.conf");
        pb.start();
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

    @GetMapping(value = "updateNginxConfig", produces = "application/json;charset=utf-8")
    public void updateNginxConfig() {
        List<UpstreamVO> upstreamList = new ArrayList<>();
        upstreamList.add(new UpstreamVO("api01", "least_conn;", Arrays.asList("127.0.0.1:9001;", "127.0.0.1:9002;")));
        upstreamList.add(new UpstreamVO("api02", "least_conn;", Arrays.asList("127.0.0.1:9003;")));

        List<LocationVO> locationList = new ArrayList<>();
        locationList.add(new LocationVO("/api01/", "http://api01;"));
        locationList.add(new LocationVO("/api02/", "http://api02;"));
        NginxConfig nginxConfig = new NginxConfig(upstreamList, locationList);
        try {
            log.info("Start refreshing Nginx configuration file: nginxConfig: {}", JSON.toJSONString(nginxConfig));
            loadBalancingService.updateNginxConfig(nginxConfig);
            log.info("Complete refreshing Nginx configuration file");
        } catch (Exception e) {
            log.error("Fail to refresh Nginx configuration file", e);
        }
    }
}
