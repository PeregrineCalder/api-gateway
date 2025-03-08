package sdk;

/**
 * @projectName: api-gateway
 * @package: sdk
 * @className: GatewayException
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class GatewayException extends RuntimeException {
    public GatewayException(String message) {
        super(message);
    }
    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
