package center.domain.manage.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    private String gatewayId;
    private String gatewayName;
    private String gatewayAddress;
    private Integer status;
}
