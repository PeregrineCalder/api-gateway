package center.domain.operation.model.vo;

import lombok.*;

/**
 * @projectName: api-gateway
 * @package: center.domain.operation.model.vo
 * @className: ApplicationInterfaceDataVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationInterfaceDataVO {
    private String systemId;
    private String interfaceId;
    private String interfaceName;
    private String interfaceVersion;

    public ApplicationInterfaceDataVO(String systemId, String interfaceId) {
        this.systemId = systemId;
        this.interfaceId = interfaceId;
    }
}
