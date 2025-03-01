package core.executor;

import core.executor.result.SessionResult;
import core.mapping.HttpStatement;

import java.util.Map;

public interface Executor {
    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
