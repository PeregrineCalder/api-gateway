import io.netty.channel.Channel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.Configuration;
import session.GenericReferenceSessionFactoryBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @projectName: api-gateway
 * @package: PACKAGE_NAME
 * @className: ApiTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class ApiTest {

    private final Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_GenericReference() throws InterruptedException, ExecutionException {
        Configuration configuration = new Configuration();
        configuration.addGenericReference("api-gateway-test", "gateway.rpc.IActivityBooth", "sayHi");

        GenericReferenceSessionFactoryBuilder builder = new GenericReferenceSessionFactoryBuilder();
        Future<Channel> future = builder.build(configuration);

        logger.info("Service start complete {}", future.get().id());

        Thread.sleep(Long.MAX_VALUE);
    }


}
