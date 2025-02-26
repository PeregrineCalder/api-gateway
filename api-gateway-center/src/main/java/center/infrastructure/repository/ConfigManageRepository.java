package center.infrastructure.repository;

import center.domain.manage.model.vo.GatewayServerDetailVO;
import center.domain.manage.model.vo.GatewayServerVO;
import center.domain.manage.repository.IConfigManageRepository;
import center.infrastructure.dao.IGatewayServerDao;
import center.infrastructure.dao.IGatewayServerDetailDao;
import center.infrastructure.po.GatewayServer;
import center.infrastructure.po.GatewayServerDetail;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.repository
 * @className: ConfigManageRepository
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Repository
public class ConfigManageRepository implements IConfigManageRepository {

    @Resource
    private IGatewayServerDao gatewayServerDao;
    @Resource
    private IGatewayServerDetailDao gatewayServerDetailDao;

    @Override
    public List<GatewayServerVO> queryGatewayServerList() {
        List<GatewayServer> gatewayServers = gatewayServerDao.queryGatewayServerList();
        List<GatewayServerVO> gatewayServerVOList = new ArrayList<>(gatewayServers.size());
        for (GatewayServer gatewayServer : gatewayServers) {
            GatewayServerVO gatewayServerVO = GatewayServerVO.builder()
                    .groupId(gatewayServer.getGroupId())
                    .groupName(gatewayServer.getGroupName())
                    .build();
            gatewayServerVOList.add(gatewayServerVO);
        }
        return gatewayServerVOList;
    }

    @Override
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress, Integer status) {
        GatewayServerDetail gatewayServerDetail = GatewayServerDetail.builder()
                .groupId(groupId)
                .gatewayId(gatewayId)
                .gatewayName(gatewayName)
                .gatewayAddress(gatewayAddress)
                .status(status)
                .build();
        gatewayServerDetailDao.insert(gatewayServerDetail);
        return true;
    }

    @Override
    public GatewayServerDetailVO queryGatewayServerDetail(String gatewayId, String gatewayAddress) {
        GatewayServerDetail req = GatewayServerDetail.builder()
                .gatewayId(gatewayId)
                .gatewayAddress(gatewayAddress)
                .build();
        GatewayServerDetail gatewayServerDetail = gatewayServerDetailDao.queryGatewayServerDetail(req);
        if (gatewayServerDetail == null) {return null;}
        return GatewayServerDetailVO.builder()
                .gatewayId(gatewayId)
                .gatewayAddress(gatewayServerDetail.getGatewayAddress())
                .gatewayName(gatewayServerDetail.getGatewayName())
                .status(gatewayServerDetail.getStatus())
                .build();
    }

    @Override
    public boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available) {
        GatewayServerDetail gatewayServerDetail = GatewayServerDetail.builder()
                .gatewayId(gatewayId)
                .gatewayAddress(gatewayAddress)
                .status(available)
                .build();
        return gatewayServerDetailDao.updateGatewayStatus(gatewayServerDetail);
    }
}
