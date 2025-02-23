package bind;

import lombok.AllArgsConstructor;
import mapping.HttpStatement;
import session.Configuration;
import session.GatewaySession;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: bind
 * @className: MapperRegistry
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
public class MapperRegistry {
    private final Configuration configuration;

    private final Map<String, MapperProxyFactory> knownMappers = new HashMap<>();

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        final MapperProxyFactory mapperProxyFactory = knownMappers.get(uri);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Uri " + uri + " is unknown to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(gatewaySession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    public void addMapper(HttpStatement httpStatement) {
        String uri = httpStatement.getUri();
        if (hasMapper(uri)) {
            throw new RuntimeException("URI " + uri + " is already registered.");
        }
        knownMappers.put(uri, new MapperProxyFactory(uri));
        configuration.addHttpStatement(httpStatement);
    }

    public <T> boolean hasMapper(String uri) {
        return knownMappers.containsKey(uri);
    }

}
