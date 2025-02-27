package center.infrastructure.dao;

import center.infrastructure.po.ApplicationInterfaceMethod;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IApplicationInterfaceMethodDao {
    void insert(ApplicationInterfaceMethod applicationInterfaceMethod);
}
