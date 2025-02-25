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
        configuration.registryConfig("api-gateway-test", "zookeeper://127.0.0.1:2181", "gateway.rpc.IActivityBooth", "1.0.0");
        HttpStatement httpStatement01 = new HttpStatement(
                "api-gateway-test",
                "gateway.rpc.IActivityBooth",
                "sayHi",
                "java.lang.String",
                "/wg/activity/sayHi",
                HttpCommandType.GET,
                false);

        HttpStatement httpStatement02 = new HttpStatement(
                "api-gateway-test",
                "gateway.rpc.IActivityBooth",
                "insert",
                "gateway.rpc.dto.XReq",
                "/wg/activity/insert",
                HttpCommandType.POST,
                true);

        configuration.addMapper(httpStatement01);
        configuration.addMapper(httpStatement02);

        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
        GatewaySocketServer gatewaySocketServer = new GatewaySocketServer(configuration, gatewaySessionFactory);
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