package center.domain.docker.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.domain.docker.model.vo
 * @className: UpstreamVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class UpstreamVO {
    private String name;
    private String strategy;
    private List<String> servers;
}
