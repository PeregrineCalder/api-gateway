package center.domain.register.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: center.domain.register.model.vo
 * @className: ApplicationSystemVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@Builder
public class ApplicationSystemVO {
    private String systemId;
    private String systemName;
    private String systemType;
    private String systemRegistry;
}
