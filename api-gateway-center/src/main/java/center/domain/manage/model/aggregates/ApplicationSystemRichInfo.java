package center.domain.manage.model.aggregates;

import center.domain.manage.model.vo.ApplicationSystemVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.domain.manage.model.aggregates
 * @className: ApplicationSystemRichInfo
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSystemRichInfo {
    private String gatewayId;
    private List<ApplicationSystemVO> applicationSystemVOList;
}
