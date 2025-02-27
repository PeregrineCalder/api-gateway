package center.domain.register.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: center.domain.register.model.vo
 * @className: ApplicationInterfaceVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@Builder
public class ApplicationInterfaceVO {
    private String systemId;
    private String interfaceId;
    private String interfaceName;
    private String interfaceVersion;
}
