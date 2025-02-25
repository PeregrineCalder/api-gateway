package bind;

import executor.result.SessionResult;

import java.util.Map;

public interface IGenericReference {
    SessionResult $invoke(Map<String, Object> params);
}