package datasource.unpooled;

import datasource.Connection;
import datasource.DataSource;
import datasource.DataSourceType;
import datasource.connection.DubboConnection;
import lombok.Setter;
import mapping.HttpStatement;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import session.Configuration;

/**
 * @projectName: api-gateway
 * @package: datasource.unpooled
 * @className: UnpooledDataSource
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Setter
public class UnpooledDataSource implements DataSource {
    private Configuration configuration;
    private HttpStatement httpStatement;
    private DataSourceType dataSourceType;

    @Override
    public Connection getConnection() {
        switch (dataSourceType) {
            case HTTP:
                // TODO
                break;
            case Dubbo:
                String application = httpStatement.getApplication();
                String interfaceName = httpStatement.getInterfaceName();
                ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
                RegistryConfig registryConfig = configuration.getRegistryConfig(application);
                ReferenceConfig<GenericService> reference = configuration.getReferenceConfig(interfaceName);
                return new DubboConnection(applicationConfig, registryConfig, reference);
            default:
                break;
        }
        throw new RuntimeException("DataSourceType：" + dataSourceType + "no corresponding datasource implementation");
    }
}
