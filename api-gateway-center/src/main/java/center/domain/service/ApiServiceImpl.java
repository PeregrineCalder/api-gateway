package center.domain.service;

import center.application.IApiService;
import center.domain.repository.IApiRepository;
import center.domain.model.ApiData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: domain.service
 * @className: ApiServiceImpl
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Service
public class ApiServiceImpl implements IApiService {
    @Resource
    private IApiRepository apiRepository;
    @Override
    public List<ApiData> queryHttpStatementList() {
        return apiRepository.queryHttpStatementList();
    }
}
