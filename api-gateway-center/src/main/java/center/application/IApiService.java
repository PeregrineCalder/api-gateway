package center.application;

import center.domain.model.ApiData;

import java.util.List;

public interface IApiService {
    List<ApiData> queryHttpStatementList();
}
