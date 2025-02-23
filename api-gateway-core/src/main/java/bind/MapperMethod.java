package bind;

import lombok.AllArgsConstructor;
import mapping.HttpCommandType;
import session.Configuration;
import session.GatewaySession;

import java.lang.reflect.Method;

/**
 * @projectName: api-gateway
 * @package: bind
 * @className: MapperMethod
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class MapperMethod {
    private String uri;
    private final HttpCommandType command;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.uri = uri;
        this.command = configuration.getHttpStatement(uri).getHttpCommandType();
    }

    public Object execute(GatewaySession session, Object args) {
        Object result = null;
        switch (command) {
            case GET:
                result = session.get(uri, args);
                break;
            case POST:
                break;
            case PUT:
                break;
            case DELETE:
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command);
        }
        return result;
    }


}
