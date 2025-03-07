package infrastructure;

import center.domain.operation.model.vo.ApplicationSystemDataVO;
import center.domain.operation.model.vo.GatewayServerDataVO;
import center.domain.operation.repository.IDataOperationManageRepository;
import center.infrastructure.common.OperationRequest;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: infrastructure
 * @className: DataOperationManageRepositoryTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class DataOperationManageRepositoryTest {
    @Resource
    private IDataOperationManageRepository repository;

    @Test
    public void test_queryGatewayServerListByPage() {
        OperationRequest<String> req = new OperationRequest<>(1, 10);
        req.setData("");
        List<GatewayServerDataVO> res = repository.queryGatewayServerListByPage(req);
        log.info("Test result: req: {}  res: {}", JSON.toJSONString(req), JSON.toJSONString(res));
    }

    @Test
    public void test_queryGatewayServerListCountByPage() {
        OperationRequest<String> req = new OperationRequest<>(1, 10);
        req.setData("10001");
        int res = repository.queryGatewayServerListCountByPage(req);
        log.info("Test result: req: {}  res: {}", JSON.toJSONString(req), JSON.toJSONString(res));
    }

    @Test
    public void test_queryApplicationSystemListByPage(){
        OperationRequest<ApplicationSystemDataVO> req = new OperationRequest<>(1, 10);
        req.setData(new ApplicationSystemDataVO("", "gateway sdk test project"));
        List<ApplicationSystemDataVO> res = repository.queryApplicationSystemListByPage(req);
        log.info("Test result: req: {}  res: {}", JSON.toJSONString(req), JSON.toJSONString(res));
    }
}
