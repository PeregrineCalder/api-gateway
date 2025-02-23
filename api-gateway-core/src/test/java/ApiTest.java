import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import mapping.HttpCommandType;
import mapping.HttpStatement;
import org.junit.jupiter.api.Test;
import session.Configuration;
import session.defaults.DefaultGatewaySessionFactory;
import socket.GatewaySocketServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @projectName: api-gateway
 * @package: PACKAGE_NAME
 * @className: ApiTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
public class ApiTest {

    @Test
    public void test_gateway() throws InterruptedException, ExecutionException {
        Configuration configuration = new Configuration();
        HttpStatement httpStatement = new HttpStatement(
                "api-gateway-test",
                "gateway.rpc.IActivityBooth",
                "sayHi",
                "/wg/activity/sayHi",
                HttpCommandType.GET
                );
        configuration.addMapper(httpStatement);
        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
        GatewaySocketServer gatewaySocketServer = new GatewaySocketServer(gatewaySessionFactory);
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(gatewaySocketServer);
        Channel channel = future.get();
        if (channel == null) {
            throw new RuntimeException("netty server start error channel is null");
        }
        while (!channel.isActive()) {
            log.info("netty server gateway start ing ...");
            Thread.sleep(100);
        }
        log.info("Service start complete {}", channel.localAddress());
        Thread.sleep(Long.MAX_VALUE);
    }


}
