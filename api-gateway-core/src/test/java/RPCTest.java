import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.SimpleReferenceCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: PACKAGE_NAME
 * @className: RPCTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class RPCTest {
    @Test
    public void test_rpc() {

        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-gateway-test");
        application.setQosEnable(false);

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setRegister(false);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("gateway.rpc.IActivityBooth");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(application)
                .registry(registry)
                .reference(reference)
                .start();

        SimpleReferenceCache cache = SimpleReferenceCache.getCache();
        GenericService genericService = cache.get(reference);

//        Object result = genericService.$invoke("sayHi", new String[]{"java.lang.String"}, new Object[]{"world"});
//        Map<String, Object> allRequestParams = new HashMap();
//        allRequestParams.put("name", "peregrine");
//        allRequestParams.put("uid", "10001");
//        Object result = genericService.$invoke("insert", new String[]{"java.lang.Object"}, new Object[]{allRequestParams});



        String[] parameterTypes = new String[]{"java.lang.String", "gateway.rpc.dto.XReq"};

        Map<String, Object> params01 = new HashMap<>();
        // params.put("class", "gateway.rpc.dto.XReq");
        params01.put("str", "10001");

        Map<String, Object> params02 = new HashMap<>();
        // params.put("str", "10001");
        params02.put("uid", "10001");
        params02.put("name", "peregrine");

//        Object user = genericService.$invoke("sayHi", new String[]{"java.lang.String"}, params.values().toArray());
//        Object user = genericService.$invoke("insert", new String[]{"gateway.rpc.dto.XReq"}, new Object[]{params});
        Object user = genericService.$invoke("test", new String[]{"java.lang.String", "gateway.rpc.dto.XReq"}, new Object[]{params01.values().toArray()[0], params02});

        System.out.println(user);
    }
}
