package assist.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: assist.domain.model.vo
 * @className: ApplicationInterfaceVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Setter
@Getter
public class ApplicationInterfaceVO {
    private String systemId;
    private String interfaceId;
    private String interfaceName;
    private String interfaceVersion;
    private List<ApplicationInterfaceMethodVO> methodList;

}
