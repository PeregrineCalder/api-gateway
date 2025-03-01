package core.bind;

import core.executor.result.SessionResult;

import java.util.Map;

public interface IGenericReference {
    SessionResult $invoke(Map<String, Object> params);
}