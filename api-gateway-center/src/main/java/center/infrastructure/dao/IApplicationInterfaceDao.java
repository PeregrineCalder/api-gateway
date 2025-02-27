package center.infrastructure.dao;

import center.infrastructure.po.ApplicationInterface;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IApplicationInterfaceDao {
    void insert(ApplicationInterface applicationInterface);
}
