package center.domain.operation.model.vo;

import lombok.*;

/**
 * @projectName: api-gateway
 * @package: center.domain.operation.model.vo
 * @className: ApplicationSystemDataVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationSystemDataVO {
    private String systemId;
    private String systemName;
    private String systemType;
    private String systemRegistry;
    public ApplicationSystemDataVO(String systemId, String systemName) {
        this.systemId = systemId;
        this.systemName = systemName;
    }
}
