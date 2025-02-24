package session.defaults;

import bind.IGenericReference;
import datasource.DataSource;
import lombok.AllArgsConstructor;
import mapping.HttpStatement;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.SimpleReferenceCache;
import org.apache.dubbo.rpc.service.GenericService;
import session.Configuration;
import session.GatewaySession;

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
    public Object get(String uri, Object parameter) {
        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        String application = httpStatement.getApplication();
        String interfaceName = httpStatement.getInterfaceName();

        ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
        RegistryConfig registryConfig = configuration.getRegistryConfig(application);
        ReferenceConfig<GenericService> reference = configuration.getReferenceConfig(interfaceName);

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig).registry(registryConfig).reference(reference).start();

        SimpleReferenceCache cache = SimpleReferenceCache.getCache();
        GenericService genericService = cache.get(reference);

        return genericService.$invoke(httpStatement.getMethodName(),
                new String[]{"java.lang.String"}, new Object[]{"peregrine"});
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
