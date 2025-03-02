package sdk.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sdk.application.GatewaySDKApplication;

/**
 * @projectName: api-gateway
 * @package: sdk.config
 * @className: GatewaySDKAutoConfig
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(GatewaySDKServiceProperties.class)
public class GatewaySDKAutoConfig {
    @Bean
    public GatewaySDKApplication gatewaySDKApplication(GatewaySDKServiceProperties properties) {
        return new GatewaySDKApplication(properties);
    }
}
