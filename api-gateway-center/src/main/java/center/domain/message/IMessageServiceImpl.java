package center.domain.message;

import center.application.IMessageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: center.domain.message
 * @className: IMessageServiceImpl
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Service
public class IMessageServiceImpl implements IMessageService {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Resource
    private Publisher publisher;

    @Override
    public Map<String, String> queryRedisConfig() {
        return new HashMap<String, String>() {{
            put("host", host);
            put("port", String.valueOf(port));
        }};
    }

    @Override
    public void pushMessage(String gatewayId, Object message) {
        publisher.pushMessage(gatewayId, message);
    }
}
