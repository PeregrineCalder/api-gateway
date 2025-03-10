package center.domain.manage.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.domain.manage.model.vo
 * @className: GatewayServerDetailVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@Builder
public class GatewayServerDetailVO {
    private Integer id;
    private String groupId;
    private String gatewayId;
    private String gatewayName;
    private String gatewayAddress;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
