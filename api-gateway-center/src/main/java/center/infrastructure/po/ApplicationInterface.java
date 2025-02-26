package center.infrastructure.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.po
 * @className: ApplicationInterface
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class ApplicationInterface {
    private Integer id;
    private String systemId;
    private String interfaceId;
    private String interfaceName;
    private String interfaceVersion;
    private Date createTime;
    private Date updateTime;
}
