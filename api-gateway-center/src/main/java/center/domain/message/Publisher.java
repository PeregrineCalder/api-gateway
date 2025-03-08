package center.domain.message;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @projectName: api-gateway
 * @package: center.domain.message
 * @className: Publisher
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
@Service
public class Publisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void pushMessage(String topic, Object message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
