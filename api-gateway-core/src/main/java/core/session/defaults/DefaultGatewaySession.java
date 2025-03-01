package core.session.defaults;

import core.bind.IGenericReference;
import core.executor.Executor;
import core.session.Configuration;
import core.session.GatewaySession;
import lombok.AllArgsConstructor;
import core.mapping.HttpStatement;

import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: session.defaults
 * @className: DefaultGatewaySession
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
public class DefaultGatewaySession implements GatewaySession {
    private Configuration configuration;
    private String uri;
    private Executor executor;

    @Override
    public Object get(String uri, Map<String, Object> params) {
        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        try {
            return executor.exec(httpStatement, params);
        } catch (Exception e) {
            throw new RuntimeException("Error exec get. Cause: " + e);
        }
    }

    @Override
    public Object post(String methodName, Map<String, Object> params) {
        return get(methodName, params);
    }

    @Override
    public IGenericReference getMapper() {
        return configuration.getMapper(uri, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
