package center.domain.manage.model.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.domain.manage.model.vo
 * @className: ApplicationSystemVO
 * @author: Peregrine Calder
 * @description: TODO
 * @date: 2025/2/27 11:03
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
    private List<ApplicationInterfaceVO> interfaceList;
}
