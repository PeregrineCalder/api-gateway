package center.infrastructure.dao;

import center.infrastructure.po.ApplicationSystem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IApplicationSystemDao {
    void insert(ApplicationSystem applicationSystem);
    List<ApplicationSystem> queryApplicationSystemList(List<String> list);
}
