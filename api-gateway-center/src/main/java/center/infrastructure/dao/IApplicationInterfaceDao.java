package center.infrastructure.dao;

import center.domain.operation.model.vo.ApplicationInterfaceDataVO;
import center.infrastructure.common.OperationRequest;
import center.infrastructure.po.ApplicationInterface;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IApplicationInterfaceDao {
    void insert(ApplicationInterface applicationInterface);
    List<ApplicationInterface> queryApplicationInterfaceList(String systemId);
    List<ApplicationInterface> queryApplicationInterfaceListByPage(OperationRequest<ApplicationInterfaceDataVO> request);
    int queryApplicationInterfaceListCountByPage(OperationRequest<ApplicationInterfaceDataVO> request);
}
