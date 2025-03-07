package center.domain.operation.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: center.domain.operation.model.vo
 * @className: GatewayServerDataVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@Builder
public class GatewayServerDataVO {
    private Integer id;
    private String groupId;
    private String groupName;
}
