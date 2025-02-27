package center.infrastructure.repository;

import center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import center.domain.register.model.vo.ApplicationInterfaceVO;
import center.domain.register.model.vo.ApplicationSystemVO;
import center.domain.register.repository.IRegisterManageRepository;
import center.infrastructure.dao.IApplicationInterfaceDao;
import center.infrastructure.dao.IApplicationInterfaceMethodDao;
import center.infrastructure.dao.IApplicationSystemDao;
import center.infrastructure.po.ApplicationInterface;
import center.infrastructure.po.ApplicationInterfaceMethod;
import center.infrastructure.po.ApplicationSystem;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @projectName: api-gateway
 * @package: center.infrastructure.repository
 * @className: RegisterManageRepository
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Repository
public class RegisterManageRepository implements IRegisterManageRepository {
    @Resource
    private IApplicationSystemDao applicationSystemDao;
    @Resource
    private IApplicationInterfaceDao applicationInterfaceDao;
    @Resource
    private IApplicationInterfaceMethodDao applicationInterfaceMethodDao;

    @Override
    public void registerApplication(ApplicationSystemVO applicationSystemVO) {
        ApplicationSystem applicationSystem = ApplicationSystem.builder()
                .systemId(applicationSystemVO.getSystemId())
                .systemName(applicationSystemVO.getSystemName())
                .systemType(applicationSystemVO.getSystemType())
                .systemRegistry(applicationSystemVO.getSystemRegistry())
                .build();
        applicationSystemDao.insert(applicationSystem);
    }

    @Override
    public void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO) {
        ApplicationInterface applicationInterface = ApplicationInterface.builder()
                .systemId(applicationInterfaceVO.getSystemId())
                .interfaceId(applicationInterfaceVO.getInterfaceId())
                .interfaceName(applicationInterfaceVO.getInterfaceName())
                .interfaceVersion(applicationInterfaceVO.getInterfaceVersion())
                .build();
        applicationInterfaceDao.insert(applicationInterface);
    }

    @Override
    public void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO) {
        ApplicationInterfaceMethod applicationInterfaceMethod = ApplicationInterfaceMethod.builder()
                .systemId(applicationInterfaceMethodVO.getSystemId())
                .interfaceId(applicationInterfaceMethodVO.getInterfaceId())
                .methodId(applicationInterfaceMethodVO.getMethodId())
                .methodName(applicationInterfaceMethodVO.getMethodName())
                .parameterType(applicationInterfaceMethodVO.getParameterType())
                .uri(applicationInterfaceMethodVO.getUri())
                .httpCommandType(applicationInterfaceMethodVO.getHttpCommandType())
                .auth(applicationInterfaceMethodVO.getAuth())
                .build();
        applicationInterfaceMethodDao.insert(applicationInterfaceMethod);
    }
}
