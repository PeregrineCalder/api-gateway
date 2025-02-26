package center.domain.manage.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: center.domain.manage.model.vo
 * @className: GatewayServerVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@Builder
public class GatewayServerVO {
    private String groupId;
    private String groupName;
}
