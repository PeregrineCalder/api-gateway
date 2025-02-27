package assist.domain.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @projectName: api-gateway
 * @package: assist.domain.model.vo
 * @className: ApplicationInterfaceMethodVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class ApplicationInterfaceMethodVO {
    private String systemId;
    private String interfaceId;
    private String methodId;
    private String methodName;
    private String parameterType;
    private String uri;
    private String httpCommandType;
    private Integer auth;
}
