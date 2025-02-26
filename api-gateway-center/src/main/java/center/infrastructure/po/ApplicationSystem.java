package center.infrastructure.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.po
 * @className: ApplicationSystem
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Getter
@Setter
public class ApplicationSystem {
    private Integer id;
    private String systemId;
    private String systemName;
    private String systemType;
    private String systemRegistry;
    private Date createTime;
    private Date updateTime;
}
