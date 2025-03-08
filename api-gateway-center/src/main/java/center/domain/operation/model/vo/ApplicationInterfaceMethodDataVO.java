package center.domain.operation.model.vo;

import lombok.*;

/**
 * @projectName: api-gateway
 * @package: center.domain.operation.model.vo
 * @className: ApplicationInterfaceMethodDataVO
 * @author: Peregrine Calder
 * @version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ApplicationInterfaceMethodDataVO {
    private String systemId;
    private String interfaceId;
    private String methodId;
    private String methodName;
    private String parameterType;
    private String uri;
    private String httpCommandType;
    private Integer auth;
    public ApplicationInterfaceMethodDataVO(String systemId, String interfaceId) {
        this.systemId = systemId;
        this.interfaceId = interfaceId;
    }
}
