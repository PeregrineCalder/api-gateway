package center.infrastructure.po;

import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.po
 * @className: GatewayServer
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class GatewayServer {
    private Integer id;
    private String groupId;
    private String groupName;
}
