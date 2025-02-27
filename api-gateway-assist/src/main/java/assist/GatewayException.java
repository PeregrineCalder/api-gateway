package assist;

/**
 * @projectName: api-gateway
 * @package: assist
 * @className: GatewayException
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class GatewayException extends RuntimeException {
    public GatewayException(String msg) {
        super(msg);
    }

    public GatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
