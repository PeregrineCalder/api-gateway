package sdk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: api-gateway
 * @package: sdk.config
 * @className: GatewaySDKServiceProperties
 * @author: Peregrine Calder
 * @version: 1.0
 */
@ConfigurationProperties("api-gateway-sdk")
@Getter
@Setter
public class GatewaySDKServiceProperties {

    private String address;
    private String systemId;
    private String systemName;
    private String systemRegistry;
}
