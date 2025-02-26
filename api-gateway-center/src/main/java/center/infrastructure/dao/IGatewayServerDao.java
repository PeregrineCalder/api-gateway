package center.infrastructure.dao;

import center.infrastructure.po.GatewayServer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGatewayServerDao {
    List<GatewayServer> queryGatewayServerList();
}
