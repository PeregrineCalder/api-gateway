package center.infrastructure.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.po
 * @className: ApplicationInterfaceMethod
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Setter
@Getter
public class ApplicationInterfaceMethod {
    private Integer id;
    private String systemId;
    private String interfaceId;
    private String methodId;
    private String methodName;
    private String parameterType;
    private String uri;
    private String httpCommandType;
    private Integer auth;
    private Date createTime;
    private Date updateTime;
}
