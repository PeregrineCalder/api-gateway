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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Map;
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
    public RedisConnectionFactory redisConnectionFactory(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService) {
        Map<String, String> redisConfig = gatewayCenterService.queryRedisConfig(properties.getAddress());
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(redisConfig.get("host"));
        standaloneConfig.setPort(Integer.parseInt(redisConfig.get("port")));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxWaitMillis(30 * 1000);
        poolConfig.setMinIdle(20);
        poolConfig.setMaxIdle(40);
        poolConfig.setTestWhileIdle(true);
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(2))
                .clientName("api-gateway-assist-redis-" + properties.getGatewayId())
                .usePooling().poolConfig(poolConfig).build();
        return new JedisConnectionFactory(standaloneConfig, clientConfig);
    }

    @Bean
    public RedisMessageListenerContainer container(GatewayServiceProperties properties, RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter msgAgreementListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(msgAgreementListenerAdapter, new PatternTopic(properties.getGatewayId()));
        return container;
    }

    @Bean
    public MessageListenerAdapter msgAgreementListenerAdapter(GatewayApplication gatewayApplication) {
        return new MessageListenerAdapter(gatewayApplication, "receiveMessage");
    }

    @Bean
    public GatewayCenterService registerGatewayService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, GatewayCenterService registerGatewayService, core.session.Configuration configuration, Channel gatewaySocketServerChannel) {
        return new GatewayApplication(properties, registerGatewayService, configuration, gatewaySocketServerChannel);
    }

    @Bean
    public core.session.Configuration gatewayCoreConfiguration(GatewayServiceProperties properties) {
        core.session.Configuration config = new core.session.Configuration();
        String[] split = properties.getGatewayAddress().split(":");
        config.setHostName(split[0].trim());
        config.setPort(Integer.parseInt(split[1].trim()));
        return config;
    }

    @Bean("gatewaySocketServerChannel")
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
