package session;

import bind.IGenericReference;
import bind.MapperRegistry;
import datasource.Connection;
import executor.Executor;
import executor.SimpleExecutor;
import mapping.HttpStatement;
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
public class Configuration {
    private final MapperRegistry mapperRegistry = new MapperRegistry(this);

    private final Map<String, HttpStatement> httpStatements = new HashMap<>();

    // Stores application-level configurations (application name -> ApplicationConfig)
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();

    // Stores registry configurations (application name -> RegistryConfig)
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();

    // Stores Dubbo generic service reference configurations (interface name -> ReferenceConfig)
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public Configuration() {
        // Initialize application configuration
        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-gateway-test");
        application.setQosEnable(false);

        // Initialize registry configuration
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setRegister(false);

        // Initialize reference configuration for Dubbo generic service invocation
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("gateway.rpc.IActivityBooth");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        applicationConfigMap.put("api-gateway-test", application);
        registryConfigMap.put("api-gateway-test", registry);
        referenceConfigMap.put("gateway.rpc.IActivityBooth", reference);
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

}
