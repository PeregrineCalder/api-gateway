package center.domain.manage.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.domain.manage.model.vo
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
    private List<ApplicationInterfaceMethodVO> methodList;
}
