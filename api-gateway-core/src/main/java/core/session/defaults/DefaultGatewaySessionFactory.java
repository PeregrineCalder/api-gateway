package core.session.defaults;

import core.datasource.DataSource;
import core.datasource.DataSourceFactory;
import core.datasource.unpooled.UnpooledDataSourceFactory;
import core.executor.Executor;
import lombok.AllArgsConstructor;
import core.session.Configuration;
import core.session.GatewaySession;
import core.session.GatewaySessionFactory;

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
        Executor executor = configuration.newExecutor(dataSource.getConnection());
        return new DefaultGatewaySession(configuration, uri, executor);
    }
}
