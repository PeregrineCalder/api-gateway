package center.domain.repository;

import center.domain.model.ApiData;

import java.util.List;

public interface IApiRepository {
    List<ApiData> queryHttpStatementList();
}
