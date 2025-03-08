package center.domain.docker.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: center.domain.docker.model.vo
 * @className: LocationVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
public class LocationVO {
    private String name;
    private String proxy_pass;
}
