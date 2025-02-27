package center.domain.register.service;

import center.application.IRegisterManageService;
import center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import center.domain.register.model.vo.ApplicationInterfaceVO;
import center.domain.register.model.vo.ApplicationSystemVO;
import center.domain.register.repository.IRegisterManageRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @projectName: api-gateway
 * @package: center.domain.register.service
 * @className: IRegisterManageServiceImpl
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Service
public class IRegisterManageServiceImpl implements IRegisterManageService {

    @Resource
    private IRegisterManageRepository registerManageRepository;


    @Override
    public void registerApplication(ApplicationSystemVO applicationSystemVO) {
        registerManageRepository.registerApplication(applicationSystemVO);
    }

    @Override
    public void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO) {
        registerManageRepository.registerApplicationInterface(applicationInterfaceVO);
    }

    @Override
    public void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO) {
        registerManageRepository.registerApplicationInterfaceMethod(applicationInterfaceMethodVO);
    }
}
