package assist.config;

import assist.application.GatewayApplication;
import assist.domain.service.GatewayCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: api-gateway
 * @package: assist.config
 * @className: GatewayAutoConfig
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(GatewayServiceProperties.class)
@Slf4j
public class GatewayAutoConfig {
    @Bean
    public GatewayCenterService registerGatewayService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, GatewayCenterService registerGatewayService) {
        return new GatewayApplication(properties, registerGatewayService);
    }

}
