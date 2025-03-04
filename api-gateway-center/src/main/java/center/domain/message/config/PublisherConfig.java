package center.domain.message.config;

import center.domain.message.util.FastJson2RedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @projectName: api-gateway
 * @package: center.domain.message.config
 * @className: PublisherConfig
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Configuration
public class PublisherConfig {

    @Bean
    public RedisTemplate<String, Object> redisMessageTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        FastJson2RedisSerializer<Object> serializer = new FastJson2RedisSerializer<>(Object.class);
        template.setDefaultSerializer(serializer);
        return template;
    }
}
