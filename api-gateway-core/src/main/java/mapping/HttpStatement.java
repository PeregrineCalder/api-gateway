package mapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @projectName: api-gateway
 * @package: mapping
 * @className: HttpStatement
 * @author: Peregrine Calder
 * @description: gateway interface mapping information
 * @version: 1.0
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpStatement {
    private String application;
    private String interfaceName;
    private String methodName;
    private String uri;
    private HttpCommandType httpCommandType;
}
