package core.executor.result;

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
public class SessionResult {
    private String code;
    private String info;
    private Object data;

    public static SessionResult buildSuccess(Object data) {
        return new SessionResult("0000", "success", data);
    }

    public static SessionResult buildError(Object data) {
        return new SessionResult("0001", "error", data);
    }
}
