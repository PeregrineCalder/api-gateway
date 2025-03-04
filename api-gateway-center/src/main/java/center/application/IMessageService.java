package center.application;

import java.util.Map;

public interface IMessageService {

    Map<String, String> queryRedisConfig();

    void pushMessage(String gatewayId, Object message);
}
