package bind;

import lombok.AllArgsConstructor;
import mapping.HttpStatement;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.objectweb.asm.Type;
import session.GatewaySession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @projectName: api-gateway
 * @package: bind
 * @className: MapperProxyFactory
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
public class MapperProxyFactory {
    private String uri;

    private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public IGenericReference newInstance(GatewaySession gatewaySession) {
        return genericReferenceCache.computeIfAbsent(uri, k -> {
            HttpStatement httpStatement = gatewaySession.getConfiguration().getHttpStatement(uri);
            MapperProxy genericReferenceProxy = new MapperProxy(gatewaySession, uri);
            InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(httpStatement.getMethodName(), Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceClass = interfaceMaker.create();
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
            enhancer.setCallback(genericReferenceProxy);
            return (IGenericReference) enhancer.create();
        });
    }
}
