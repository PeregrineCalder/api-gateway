package core.bind;

import lombok.AllArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import core.session.GatewaySession;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: bind
 * @className: MapperProxy
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
public class MapperProxy implements MethodInterceptor {
    private GatewaySession gatewaySession;
    private final String uri;

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) {
        MapperMethod linkMethod = new MapperMethod(uri, method, gatewaySession.getConfiguration());
        return linkMethod.execute(gatewaySession, (Map<String, Object>) args[0]);
    }
}
