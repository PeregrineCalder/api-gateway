package session.defaults;

import bind.IGenericReference;
import datasource.Connection;
import datasource.DataSource;
import lombok.AllArgsConstructor;
import mapping.HttpStatement;
import session.Configuration;
import session.GatewaySession;
import type.SimpleTypeRegistry;

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
    private DataSource dataSource;

    @Override
    public Object get(String uri, Map<String, Object> params) {
        Connection connection = dataSource.getConnection();
        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        String parameterType = httpStatement.getParameterType();

        return connection.execute(httpStatement.getMethodName(),
                new String[]{parameterType},
                new String[]{"ignore"},
                SimpleTypeRegistry.isSimpleType(parameterType) ? params.values().toArray() : new Object[]{params}
        );
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
