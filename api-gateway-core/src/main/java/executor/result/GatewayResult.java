package executor.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @projectName: api-gateway
 * @package: executor.result
 * @className: GatewayResult
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
@Getter
public class GatewayResult {
    private String code;
    private String info;
    private Object data;

    public static GatewayResult buildSuccess(Object data) {
        return new GatewayResult("0000", "success", data);
    }

    public static GatewayResult buildError(Object data) {
        return new GatewayResult("0001", "error", data);
    }
}
