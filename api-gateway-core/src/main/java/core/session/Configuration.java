package core.session;

import core.authorization.IAuth;
import core.authorization.auth.AuthService;
import core.bind.IGenericReference;
import core.bind.MapperRegistry;
import core.datasource.Connection;
import core.executor.Executor;
import core.executor.SimpleExecutor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import core.mapping.HttpStatement;
import lombok.Setter;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: session
 * @className: Configuration
 * @author: Peregrine Calder
 * @description: session life-circle configuration
 * @version: 1.0
 */
@NoArgsConstructor
@Getter
@Setter
public class Configuration {

    private String hostName = "127.0.0.1";

    private int port = 7397;

    private int bossNThreads = 1;

    private int workNThreads = 4;

    private final MapperRegistry mapperRegistry = new MapperRegistry(this);

    private final Map<String, HttpStatement> httpStatements = new HashMap<>();

    private final IAuth auth = new AuthService();

    // Stores application-level configurations (application name -> ApplicationConfig)
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();

    // Stores registry configurations (application name -> RegistryConfig)
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();

    // Stores Dubbo generic service reference configurations (interface name -> ReferenceConfig)
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public synchronized void registryConfig(String applicationName, String address, String interfaceName, String version) {
        if (applicationConfigMap.get(applicationName) == null) {
            ApplicationConfig application = new ApplicationConfig();
            application.setName(applicationName);
            application.setQosEnable(false);
            applicationConfigMap.put(applicationName, application);
        }

        if (registryConfigMap.get(applicationName) == null) {
            RegistryConfig registry = new RegistryConfig();
            registry.setAddress(address);
            registry.setRegister(false);
            registryConfigMap.put(applicationName, registry);
        }

        if (referenceConfigMap.get(interfaceName) == null) {
            ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
            reference.setInterface(interfaceName);
            reference.setVersion(version);
            reference.setGeneric("true");
            referenceConfigMap.put(interfaceName, reference);
        }
    }

    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public void addMapper(HttpStatement httpStatement) {
        mapperRegistry.addMapper(httpStatement);
    }

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        return mapperRegistry.getMapper(uri, gatewaySession);
    }

    public void addHttpStatement(HttpStatement httpStatement) {
        httpStatements.put(httpStatement.getUri(), httpStatement);
    }

    public HttpStatement getHttpStatement(String uri) {
        return httpStatements.get(uri);
    }

    public Executor newExecutor(Connection connection) {
        return new SimpleExecutor(this, connection);
    }

    public boolean authValidate(String uId, String token) {
        return auth.validate(uId, token);
    }
}
