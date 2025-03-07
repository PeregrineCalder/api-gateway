package center.domain.manage.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.domain.manage.model.vo
 * @className: GatewayDistributionVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@Builder
public class GatewayDistributionVO {
    private Integer id;
    private String groupId;
    private String gatewayId;
    private String systemId;
    private String systemName;
    private Date createTime;
    private Date updateTime;
}
