package center.domain.manage.service;

import center.application.IConfigManageService;
import center.domain.manage.model.vo.GatewayServerDetailVO;
import center.domain.manage.model.vo.GatewayServerVO;
import center.domain.manage.repository.IConfigManageRepository;
import center.infrastructure.common.Constants;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: center.domain.manage.service
 * @className: ConfigManageService
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Service
public class ConfigManageService implements IConfigManageService {
    @Resource
    private IConfigManageRepository configManageRepository;

    @Override
    public List<GatewayServerVO> queryGatewayServerList() {
        return configManageRepository.queryGatewayServerList();
    }

    @Override
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        GatewayServerDetailVO gatewayServerDetailVO = configManageRepository.queryGatewayServerDetail(gatewayId, gatewayName);
        if (gatewayServerDetailVO == null) {
            try {
                return configManageRepository.registerGatewayServerNode(groupId, gatewayId, gatewayName, gatewayAddress, Constants.GatewayStatus.Available);
            } catch (DuplicateKeyException e) {
                return configManageRepository.updateGatewayStatus(gatewayId, gatewayAddress, Constants.GatewayStatus.Available);
            }
        } else {
            return configManageRepository.updateGatewayStatus(gatewayId, gatewayAddress, Constants.GatewayStatus.Available);
        }
    }
}
