package center.infrastructure.dao;

import center.domain.operation.model.vo.GatewayDistributionDataVO;
import center.infrastructure.common.OperationRequest;
import center.infrastructure.po.GatewayDistribution;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGatewayDistributionDao {

    List<String> queryGatewayDistributionSystemIdList();

    String queryGatewayDistribution(String systemId);

    List<GatewayDistribution> queryGatewayDistributionList();

    List<GatewayDistribution> queryGatewayDistributionListByPage(OperationRequest<GatewayDistributionDataVO> request);

    int queryGatewayDistributionListCountByPage(OperationRequest<GatewayDistributionDataVO> request);
}
