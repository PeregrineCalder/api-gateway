package assist.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: assist.domain.model.vo
 * @className: ApplicationSystemVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class ApplicationSystemVO {
    private String systemId;
    private String systemName;
    private String systemType;
    private String systemRegistry;
    private List<ApplicationInterfaceVO> interfaceList;
}
