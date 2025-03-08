package center.infrastructure.dao;

import center.domain.operation.model.vo.ApplicationSystemDataVO;
import center.infrastructure.common.OperationRequest;
import center.infrastructure.po.ApplicationSystem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IApplicationSystemDao {
    void insert(ApplicationSystem applicationSystem);
    List<ApplicationSystem> queryApplicationSystemList(List<String> list);
    List<ApplicationSystem> queryApplicationSystemListByPage(OperationRequest<ApplicationSystemDataVO> request);
    int queryApplicationSystemListCountByPage(OperationRequest<ApplicationSystemDataVO> request);
}
