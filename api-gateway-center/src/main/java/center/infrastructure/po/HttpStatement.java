package center.infrastructure.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: infrastructure.po
 * @className: HttpStatement
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class HttpStatement {
    private Integer id;
    private String application;
    private String interfaceName;
    private String methodName;
    private String parameterType;
    private String uri;
    private String httpCommandType;
    private Integer auth;
    private Date createTime;
    private Date updateTime;
}
