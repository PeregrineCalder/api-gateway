package assist.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @projectName: api-gateway
 * @package: assist.config
 * @className: GatewayServiceProperties
 * @author: Peregrine Calder
 * @version: 1.0
 */
@ConfigurationProperties("api-gateway")
@Getter
@Setter
public class GatewayServiceProperties {
    private String address;
    private String groupId;
    private String gatewayId;
    private String gatewayName;
    private String gatewayAddress;
}
