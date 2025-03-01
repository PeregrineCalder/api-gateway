package core.bind;

import core.mapping.HttpCommandType;
import core.session.Configuration;
import core.session.GatewaySession;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: bind
 * @className: MapperMethod
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class MapperMethod {
    private String methodName;
    private final HttpCommandType command;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.methodName = configuration.getHttpStatement(uri).getMethodName();
        this.command = configuration.getHttpStatement(uri).getHttpCommandType();
    }

    public Object execute(GatewaySession session, Map<String, Object> params) {
        Object result = null;
        switch (command) {
            case GET:
                result = session.get(methodName, params);
                break;
            case POST:
                result = session.post(methodName, params);
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
