package assist.config;

import assist.application.GatewayApplication;
import assist.domain.service.GatewayCenterService;
import core.session.defaults.DefaultGatewaySessionFactory;
import core.socket.GatewaySocketServer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @projectName: api-gateway
 * @package: assist.config
 * @className: GatewayAutoConfig
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(GatewayServiceProperties.class)
@Slf4j
public class GatewayAutoConfig {
    @Bean
    public GatewayCenterService registerGatewayService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, GatewayCenterService registerGatewayService, core.session.Configuration configuration) {
        return new GatewayApplication(properties, registerGatewayService, configuration);
    }

    @Bean
    public core.session.Configuration gatewayCoreConfiguration(GatewayServiceProperties properties) {
        core.session.Configuration config = new core.session.Configuration();
        String[] split = properties.getGatewayAddress().split(":");
        config.setHostName(split[0]);
        config.setPort(Integer.parseInt(split[1].trim()));
        return config;
    }

    @Bean
    public Channel initGateway(core.session.Configuration config) throws ExecutionException, InterruptedException {
        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(config);
        GatewaySocketServer server = new GatewaySocketServer(config, gatewaySessionFactory);
        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();
        if (!channel.isActive()) {
            log.info("api gateway core netty server gateway start ing...");
            Thread.sleep(500);
        }
        log.info("api gateway core netty server gateway start success: {}", channel.localAddress());
        return channel;
    }

}
