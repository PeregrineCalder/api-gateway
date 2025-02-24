package session.defaults;

import datasource.DataSource;
import datasource.DataSourceFactory;
import datasource.unpooled.UnpooledDataSourceFactory;
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
    public GatewaySession openSession(String uri) {
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        return new DefaultGatewaySession(configuration, uri, dataSource);
    }
}
