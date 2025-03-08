package center.application;

import center.domain.docker.model.aggregates.NginxConfig;

public interface ILoadBalancingService {
    void updateNginxConfig(NginxConfig nginxConfig) throws Exception;
}
