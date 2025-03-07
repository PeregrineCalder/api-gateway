package center.domain.operation.model.vo;

import lombok.*;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.domain.operation.model.vo
 * @className: GatewayServerDetailDataVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayServerDetailDataVO {
    private Integer id;
    private String groupId;
    private String gatewayId;
    private String gatewayName;
    private String gatewayAddress;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public GatewayServerDetailDataVO(String groupId, String gatewayId) {
        this.groupId = groupId;
        this.gatewayId = gatewayId;
    }
}
