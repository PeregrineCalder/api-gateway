package center.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: domain.model
 * @className: ApiData
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
@Builder
public class ApiData {
    private String application;
    private String interfaceName;
    private String methodName;
    private String parameterType;
    private String uri;
    private String httpCommandType;
    private Integer auth;
}
