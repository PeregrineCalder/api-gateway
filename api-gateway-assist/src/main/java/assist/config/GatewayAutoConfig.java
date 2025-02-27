package assist.config;

import assist.application.GatewayApplication;
import assist.service.RegisterGatewayService;
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
    public RegisterGatewayService registerGatewayService() {
        return new RegisterGatewayService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, RegisterGatewayService registerGatewayService) {
        return new GatewayApplication(properties, registerGatewayService);
    }

}
