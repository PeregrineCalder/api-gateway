package center.infrastructure.dao;

import center.infrastructure.po.ApplicationInterface;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IApplicationInterfaceDao {
    void insert(ApplicationInterface applicationInterface);
    List<ApplicationInterface> queryApplicationInterfaceList(String systemId);
}
