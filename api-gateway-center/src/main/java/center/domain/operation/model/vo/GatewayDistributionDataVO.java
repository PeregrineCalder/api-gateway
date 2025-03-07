package center.domain.operation.model.vo;

import lombok.*;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.domain.operation.model.vo
 * @className: GatewayDistributionDataVO
 * @author: Peregrine Calder
 * @description: TODO
 * @date: 2025/3/6 08:36
 * @version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayDistributionDataVO {
    private Integer id;
    private String groupId;
    private String gatewayId;
    private String systemId;
    private String systemName;
    private Date createTime;
    private Date updateTime;

    public GatewayDistributionDataVO(String groupId, String gatewayId) {
        this.groupId = groupId;
        this.gatewayId = gatewayId;
    }

}
