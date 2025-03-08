package center.domain.docker.service;

import center.application.ILoadBalancingService;
import center.domain.docker.model.aggregates.NginxConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @projectName: api-gateway
 * @package: center.domain.docker.service
 * @className: AbstractLoadBalancing
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
public abstract class AbstractLoadBalancing implements ILoadBalancingService {

    @Override
    public void updateNginxConfig(NginxConfig nginxConfig) throws Exception {
        String containerFilePath = createNginxConfigFile(nginxConfig);
        log.info("Step 1: Create Nginx configuration file: containerFilePath：{}", containerFilePath);
        refreshNginxConfig(nginxConfig.getNginxName());
        log.info("Step 2: Refresh Nginx configuration file Done！");
    }

    protected abstract String createNginxConfigFile(NginxConfig nginxConfig) throws IOException;

    protected abstract void copyDockerFile(String applicationName, String containerFilePath, String localNginxPath) throws InterruptedException, IOException;

    protected abstract void refreshNginxConfig(String nginxName) throws InterruptedException, IOException;

}
