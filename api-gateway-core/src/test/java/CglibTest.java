import com.alibaba.fastjson2.JSON;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @projectName: api-gateway
 * @package: PACKAGE_NAME
 * @className: CglibTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class CglibTest implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return JSON.toJSON(objects);
    }

    @Test
    public void test_cglib() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        interfaceMaker.add(new Signature("sayHi", Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
        Class<?> interfaceClass = interfaceMaker.create();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Object.class);
        enhancer.setInterfaces(new Class[]{interfaceClass});
        enhancer.setCallback(this);
        Object obj = enhancer.create();

        Method method = obj.getClass().getMethod("sayHi", String.class);
        Object result = method.invoke(obj,"hi peregrine");

        System.out.println(result);
    }
}
