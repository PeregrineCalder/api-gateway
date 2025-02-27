package center.domain.register.repository;

import center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import center.domain.register.model.vo.ApplicationInterfaceVO;
import center.domain.register.model.vo.ApplicationSystemVO;

public interface IRegisterManageRepository {

    void registerApplication(ApplicationSystemVO applicationSystemVO);

    void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO);

    void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO);
}
