package assist.domain.model.aggregates;

import assist.domain.model.vo.ApplicationSystemVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: assist.domain.model.aggregates
 * @className: ApplicationSystemRichInfo
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Setter
@Getter
public class ApplicationSystemRichInfo {
    private String gatewayId;
    private List<ApplicationSystemVO> applicationSystemVOList;
}
