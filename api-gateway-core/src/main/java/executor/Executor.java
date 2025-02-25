package executor;

import executor.result.SessionResult;
import mapping.HttpStatement;

import java.util.Map;

public interface Executor {
    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
