import center.application.IConfigManageService;
import center.domain.manage.model.vo.GatewayServerVO;
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
 * @package: PACKAGE_NAME
 * @className: ApiTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ApiTest {
    @Resource
    private IConfigManageService configManageService;

    @Test
    public void test_queryGatewayServerList() {
        List<GatewayServerVO> gatewayServerVOS = configManageService.queryGatewayServerList();
        log.info("Test result: {}", JSON.toJSONString(gatewayServerVOS));
    }

    @Test
    public void test_registerGatewayServerNode() {
        configManageService.registerGatewayServerNode("10001", "api-gateway-g1", "payment gateway", "127.0.0.196");
        configManageService.registerGatewayServerNode("10001", "api-gateway-g2", "payment gateway", "127.0.0.197");
        configManageService.registerGatewayServerNode("10001", "api-gateway-g3", "delivery gateway", "127.0.0.198");
    }
}
