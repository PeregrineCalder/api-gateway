package center.infrastructure.repository;

import center.domain.model.ApiData;
import center.domain.repository.IApiRepository;
import center.infrastructure.dao.IHttpStatementDao;
import center.infrastructure.po.HttpStatement;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: api-gateway
 * @package: infrastructure.repository
 * @className: ApiRepository
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Component
public class ApiRepository implements IApiRepository {
    @Resource
    private IHttpStatementDao httpStatementDao;

    @Override
    public List<ApiData> queryHttpStatementList() {
        List<HttpStatement> httpStatements = httpStatementDao.queryHttpStatementList();
        List<ApiData> apiDataList = new ArrayList<>(httpStatements.size());
        for (HttpStatement httpStatement : httpStatements) {
            ApiData apiData = ApiData.builder()
                    .application(httpStatement.getApplication())
                    .interfaceName(httpStatement.getInterfaceName())
                    .methodName(httpStatement.getMethodName())
                    .parameterType(httpStatement.getParameterType())
                    .uri(httpStatement.getUri())
                    .httpCommandType(httpStatement.getHttpCommandType())
                    .auth(httpStatement.getAuth())
                    .build();
            apiDataList.add(apiData);
        }
        return apiDataList;
    }
}
