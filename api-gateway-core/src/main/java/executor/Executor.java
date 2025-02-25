package executor;

import executor.result.GatewayResult;
import mapping.HttpStatement;

import java.util.Map;

public interface Executor {
    GatewayResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
