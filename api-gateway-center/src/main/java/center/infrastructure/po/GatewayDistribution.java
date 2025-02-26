package center.infrastructure.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.po
 * @className: GatewayDistribution
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class GatewayDistribution {
    private Integer id;
    private String groupId;
    private String gatewayId;
    private String systemId;
    private String systemName;
    private Date createTime;
    private Date updateTime;
}
