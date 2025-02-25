package executor;

import com.alibaba.fastjson2.JSON;
import datasource.Connection;
import executor.result.SessionResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapping.HttpStatement;
import session.Configuration;
import type.SimpleTypeRegistry;

import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: executor
 * @className: BaseExecutor
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
@AllArgsConstructor
public abstract class BaseExecutor implements Executor {
    protected Configuration configuration;
    protected Connection connection;

    @Override
    public SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) {
        String methodName = httpStatement.getMethodName();
        String parameterType = httpStatement.getParameterType();
        String[] parameterTypes = new String[]{parameterType};
        Object[] args = SimpleTypeRegistry.isSimpleType(parameterType) ?
                params.values().toArray(new Object[0]) : new Object[]{params};
        log.info("Executing method: {}#{}.{}({}) args：{}", httpStatement.getApplication(),
                httpStatement.getInterfaceName(), httpStatement.getMethodName(),
                JSON.toJSONString(parameterTypes), JSON.toJSONString(args));
        try {
            Object data = doExec(methodName, parameterTypes, args);
            return SessionResult.buildSuccess(data);
        } catch (Exception e) {
            return SessionResult.buildError(e.getMessage());
        }
    }

    protected abstract Object doExec(String methodName, String[] parameterTypes, Object[] args) throws Exception;
}
