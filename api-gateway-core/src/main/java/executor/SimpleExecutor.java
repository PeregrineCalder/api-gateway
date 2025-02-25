package executor;

import datasource.Connection;
import session.Configuration;

/**
 * @projectName: api-gateway
 * @package: executor
 * @className: SimpleExecutor
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Connection connection) {
        super(configuration, connection);
    }

    @Override
    protected Object doExec(String methodName, String[] parameterTypes, Object[] args) {
        return connection.execute(methodName, parameterTypes, new String[]{"ignore"}, args);
    }
}
