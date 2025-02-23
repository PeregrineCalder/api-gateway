package session.defaults;

import lombok.AllArgsConstructor;
import session.Configuration;
import session.GatewaySession;
import session.GatewaySessionFactory;

/**
 * @projectName: api-gateway
 * @package: session.defaults
 * @className: DefaultGatewaySessionFactory
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
public class DefaultGatewaySessionFactory implements GatewaySessionFactory {
    private final Configuration configuration;

    @Override
    public GatewaySession openSession() {
        return new DefaultGatewaySession(configuration);
    }
}
