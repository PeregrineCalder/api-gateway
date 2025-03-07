package center.infrastructure.repository;

import center.domain.manage.model.vo.*;
import center.domain.manage.repository.IConfigManageRepository;
import center.infrastructure.dao.*;
import center.infrastructure.po.*;
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
    @Resource
    private IGatewayDistributionDao gatewayDistributionDao;
    @Resource
    private IApplicationSystemDao applicationSystemDao;
    @Resource
    private IApplicationInterfaceDao applicationInterfaceDao;
    @Resource
    private IApplicationInterfaceMethodDao applicationInterfaceMethodDao;

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
    public List<GatewayServerDetailVO> queryGatewayServerDetailList() {
        List<GatewayServerDetail> gatewayServerDetails = gatewayServerDetailDao.queryGatewayServerDetailList();
        List<GatewayServerDetailVO> gatewayServerDetailVOList = new ArrayList<>(gatewayServerDetails.size());
        for (GatewayServerDetail gatewayServerDetail : gatewayServerDetails) {
            GatewayServerDetailVO gatewayServerDetailVO = GatewayServerDetailVO.builder()
                    .id(gatewayServerDetail.getId())
                    .groupId(gatewayServerDetail.getGroupId())
                    .gatewayId(gatewayServerDetail.getGatewayId())
                    .gatewayName(gatewayServerDetail.getGatewayName())
                    .gatewayAddress(gatewayServerDetail.getGatewayAddress())
                    .status(gatewayServerDetail.getStatus())
                    .createTime(gatewayServerDetail.getCreateTime())
                    .updateTime(gatewayServerDetail.getUpdateTime())
                    .build();
            gatewayServerDetailVOList.add(gatewayServerDetailVO);
        }
        return gatewayServerDetailVOList;
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

    @Override
    public List<String> queryGatewayDistributionSystemIdList(String gatewayId) {
        return gatewayDistributionDao.queryGatewayDistributionSystemIdList();
    }

    @Override
    public List<ApplicationSystemVO> queryApplicationSystemList(List<String> systemIdList) {
        List<ApplicationSystem> applicationSystemList = applicationSystemDao.queryApplicationSystemList(systemIdList);
        List<ApplicationSystemVO> applicationSystemVOList = new ArrayList<>(applicationSystemList.size());
        for (ApplicationSystem applicationSystem : applicationSystemList) {
            ApplicationSystemVO applicationSystemVO = ApplicationSystemVO.builder()
                    .systemId(applicationSystem.getSystemId())
                    .systemName(applicationSystem.getSystemName())
                    .systemType(applicationSystem.getSystemType())
                    .systemRegistry(applicationSystem.getSystemRegistry())
                    .build();
            applicationSystemVOList.add(applicationSystemVO);
        }
        return applicationSystemVOList;
    }

    @Override
    public List<ApplicationInterfaceVO> queryApplicationInterfaceList(String systemId) {
        List<ApplicationInterface> applicationInterfaces = applicationInterfaceDao.queryApplicationInterfaceList(systemId);
        List<ApplicationInterfaceVO> applicationInterfaceVOList = new ArrayList<>(applicationInterfaces.size());
        for (ApplicationInterface applicationInterface : applicationInterfaces) {
            ApplicationInterfaceVO applicationInterfaceVO = ApplicationInterfaceVO.builder()
                    .systemId(applicationInterface.getSystemId())
                    .interfaceId(applicationInterface.getInterfaceId())
                    .interfaceName(applicationInterface.getInterfaceName())
                    .interfaceVersion(applicationInterface.getInterfaceVersion())
                    .build();
            applicationInterfaceVOList.add(applicationInterfaceVO);
        }
        return applicationInterfaceVOList;
    }

    @Override
    public List<ApplicationInterfaceMethodVO> queryApplicationInterfaceMethodList(String systemId, String interfaceId) {
        ApplicationInterfaceMethod req = ApplicationInterfaceMethod.builder()
                .systemId(systemId)
                .interfaceId(interfaceId)
                .build();
        List<ApplicationInterfaceMethod> applicationInterfaceMethods = applicationInterfaceMethodDao.queryApplicationInterfaceMethodList(req);
        List<ApplicationInterfaceMethodVO> applicationInterfaceMethodVOList = new ArrayList<>(applicationInterfaceMethods.size());
        for (ApplicationInterfaceMethod applicationInterfaceMethod : applicationInterfaceMethods) {
            ApplicationInterfaceMethodVO applicationInterfaceMethodVO = ApplicationInterfaceMethodVO.builder()
                    .systemId(applicationInterfaceMethod.getSystemId())
                    .interfaceId(applicationInterfaceMethod.getInterfaceId())
                    .methodId(applicationInterfaceMethod.getMethodId())
                    .methodName(applicationInterfaceMethod.getMethodName())
                    .parameterType(applicationInterfaceMethod.getParameterType())
                    .uri(applicationInterfaceMethod.getUri())
                    .httpCommandType(applicationInterfaceMethod.getHttpCommandType())
                    .auth(applicationInterfaceMethod.getAuth())
                    .build();
            applicationInterfaceMethodVOList.add(applicationInterfaceMethodVO);
        }
        return applicationInterfaceMethodVOList;
    }

    @Override
    public String queryGatewayDistribution(String systemId) {
        return gatewayDistributionDao.queryGatewayDistribution(systemId);
    }

    @Override
    public List<GatewayDistributionVO> queryGatewayDistributionList() {
        List<GatewayDistribution> gatewayDistributionList = gatewayDistributionDao.queryGatewayDistributionList();
        List<GatewayDistributionVO> gatewayDistributionVOList = new ArrayList<>(gatewayDistributionList.size());
        for (GatewayDistribution gatewayDistribution : gatewayDistributionList) {
            GatewayDistributionVO gatewayDistributionVO = GatewayDistributionVO.builder()
                    .id(gatewayDistribution.getId())
                    .gatewayId(gatewayDistribution.getGatewayId())
                    .groupId(gatewayDistribution.getGroupId())
                    .systemId(gatewayDistribution.getSystemId())
                    .systemName(gatewayDistribution.getSystemName())
                    .createTime(gatewayDistribution.getCreateTime())
                    .updateTime(gatewayDistribution.getUpdateTime())
                    .build();
            gatewayDistributionVOList.add(gatewayDistributionVO);
        }
        return gatewayDistributionVOList;
    }
}
