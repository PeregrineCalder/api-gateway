package center.infrastructure.repository;

import center.domain.operation.model.vo.*;
import center.domain.operation.repository.IDataOperationManageRepository;
import center.infrastructure.common.OperationRequest;
import center.infrastructure.dao.*;
import center.infrastructure.po.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.repository
 * @className: DataOperationManageRepository
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Component
public class DataOperationManageRepository implements IDataOperationManageRepository {

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
    public List<GatewayServerDataVO> queryGatewayServerListByPage(OperationRequest<String> request) {
        List<GatewayServer> gatewayServers = gatewayServerDao.queryGatewayServerListByPage(request);
        List<GatewayServerDataVO> gatewayServerVOList = new ArrayList<>(gatewayServers.size());
        for (GatewayServer gatewayServer : gatewayServers) {
            GatewayServerDataVO gatewayServerDataVO = GatewayServerDataVO.builder()
                    .id(gatewayServer.getId())
                    .groupId(gatewayServer.getGroupId())
                    .groupName(gatewayServer.getGroupName())
                    .build();
            gatewayServerVOList.add(gatewayServerDataVO);
        }
        return gatewayServerVOList;
    }

    @Override
    public int queryGatewayServerListCountByPage(OperationRequest<String> request) {
        return gatewayServerDao.queryGatewayServerListCountByPage(request);
    }

    @Override
    public List<ApplicationSystemDataVO> queryApplicationSystemListByPage(OperationRequest<ApplicationSystemDataVO> request) {
        List<ApplicationSystem> applicationSystems = applicationSystemDao.queryApplicationSystemListByPage(request);
        List<ApplicationSystemDataVO> applicationSystemDataVOList = new ArrayList<>(applicationSystems.size());
        for (ApplicationSystem applicationSystem : applicationSystems) {
            ApplicationSystemDataVO applicationSystemDataVO = ApplicationSystemDataVO.builder()
                    .systemId(applicationSystem.getSystemId())
                    .systemName(applicationSystem.getSystemName())
                    .systemType(applicationSystem.getSystemType())
                    .systemRegistry(applicationSystem.getSystemRegistry())
                    .build();
            applicationSystemDataVOList.add(applicationSystemDataVO);
        }
        return applicationSystemDataVOList;
    }

    @Override
    public int queryApplicationSystemListCountByPage(OperationRequest<ApplicationSystemDataVO> request) {
        return applicationSystemDao.queryApplicationSystemListCountByPage(request);
    }

    @Override
    public List<ApplicationInterfaceDataVO> queryApplicationInterfaceListByPage(OperationRequest<ApplicationInterfaceDataVO> request) {
        List<ApplicationInterface> applicationInterfaces = applicationInterfaceDao.queryApplicationInterfaceListByPage(request);
        List<ApplicationInterfaceDataVO> applicationInterfaceDataVOList = new ArrayList<>(applicationInterfaces.size());
        for (ApplicationInterface applicationInterface : applicationInterfaces) {
            ApplicationInterfaceDataVO applicationInterfaceDataVO = ApplicationInterfaceDataVO.builder()
                    .interfaceId(applicationInterface.getInterfaceId())
                    .interfaceName(applicationInterface.getInterfaceName())
                    .systemId(applicationInterface.getSystemId())
                    .interfaceVersion(applicationInterface.getInterfaceVersion())
                    .build();
            applicationInterfaceDataVOList.add(applicationInterfaceDataVO);
        }
        return applicationInterfaceDataVOList;
    }

    @Override
    public int queryApplicationInterfaceListCountByPage(OperationRequest<ApplicationInterfaceDataVO> request) {
        return applicationInterfaceDao.queryApplicationInterfaceListCountByPage(request);
    }

    @Override
    public List<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethodListByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        List<ApplicationInterfaceMethod> applicationInterfaceMethods = applicationInterfaceMethodDao.queryApplicationInterfaceMethodListByPage(request);
        List<ApplicationInterfaceMethodDataVO> applicationInterfaceMethodDataVOList = new ArrayList<>(applicationInterfaceMethods.size());
        for (ApplicationInterfaceMethod applicationInterfaceMethod : applicationInterfaceMethods) {
            ApplicationInterfaceMethodDataVO applicationInterfaceMethodDataVO = ApplicationInterfaceMethodDataVO.builder()
                    .interfaceId(applicationInterfaceMethod.getInterfaceId())
                    .methodId(applicationInterfaceMethod.getMethodId())
                    .methodName(applicationInterfaceMethod.getMethodName())
                    .systemId(applicationInterfaceMethod.getSystemId())
                    .parameterType(applicationInterfaceMethod.getParameterType())
                    .uri(applicationInterfaceMethod.getUri())
                    .httpCommandType(applicationInterfaceMethod.getHttpCommandType())
                    .auth(applicationInterfaceMethod.getAuth())
                    .build();
            applicationInterfaceMethodDataVOList.add(applicationInterfaceMethodDataVO);
        }
        return applicationInterfaceMethodDataVOList;
    }

    @Override
    public int queryApplicationInterfaceMethodListCountByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        return applicationInterfaceMethodDao.queryApplicationInterfaceMethodListCountByPage(request);
    }

    @Override
    public List<GatewayServerDetailDataVO> queryGatewayServerDetailListByPage(OperationRequest<GatewayServerDetailDataVO> request) {
        List<GatewayServerDetail> applicationInterfaceMethods = gatewayServerDetailDao.queryGatewayServerDetailListByPage(request);
        List<GatewayServerDetailDataVO> gatewayServerDetailDataVOList = new ArrayList<>(applicationInterfaceMethods.size());
        for (GatewayServerDetail gatewayServerDetail : applicationInterfaceMethods) {
            GatewayServerDetailDataVO gatewayServerDetailDataVO = GatewayServerDetailDataVO.builder()
                    .id(gatewayServerDetail.getId())
                    .groupId(gatewayServerDetail.getGroupId())
                    .gatewayId(gatewayServerDetail.getGatewayId())
                    .gatewayName(gatewayServerDetail.getGatewayName())
                    .gatewayAddress(gatewayServerDetail.getGatewayAddress())
                    .status(gatewayServerDetail.getStatus())
                    .createTime(gatewayServerDetail.getCreateTime())
                    .updateTime(gatewayServerDetail.getUpdateTime())
                    .build();
            gatewayServerDetailDataVOList.add(gatewayServerDetailDataVO);
        }
        return gatewayServerDetailDataVOList;
    }

    @Override
    public int queryGatewayServerDetailListCountByPage(OperationRequest<GatewayServerDetailDataVO> request) {
        return gatewayServerDetailDao.queryGatewayServerDetailListCountByPage(request);
    }


    @Override
    public List<GatewayDistributionDataVO> queryGatewayDistributionListByPage(OperationRequest<GatewayDistributionDataVO> request) {
        List<GatewayDistribution> gatewayDistributions = gatewayDistributionDao.queryGatewayDistributionListByPage(request);
        List<GatewayDistributionDataVO> gatewayServerDetailDataVOList = new ArrayList<>(gatewayDistributions.size());
        for (GatewayDistribution gatewayDistribution : gatewayDistributions) {
            GatewayDistributionDataVO gatewayDistributionDataVO = GatewayDistributionDataVO.builder()
                    .id(gatewayDistribution.getId())
                    .groupId(gatewayDistribution.getGroupId())
                    .gatewayId(gatewayDistribution.getGatewayId())
                    .systemId(gatewayDistribution.getSystemId())
                    .systemName(gatewayDistribution.getSystemName())
                    .createTime(gatewayDistribution.getCreateTime())
                    .updateTime(gatewayDistribution.getUpdateTime())
                    .build();
            gatewayServerDetailDataVOList.add(gatewayDistributionDataVO);
        }
        return gatewayServerDetailDataVOList;
    }

    @Override
    public int queryGatewayDistributionListCountByPage(OperationRequest<GatewayDistributionDataVO> request) {
        return gatewayDistributionDao.queryGatewayDistributionListCountByPage(request);
    }
}
