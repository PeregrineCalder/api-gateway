package center.infrastructure.dao;

import center.infrastructure.po.ApplicationSystem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IApplicationSystemDao {
    void insert(ApplicationSystem applicationSystem);
}
