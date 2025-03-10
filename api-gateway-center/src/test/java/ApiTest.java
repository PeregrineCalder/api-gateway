import center.application.IConfigManageService;
import center.application.IRegisterManageService;
import center.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import center.domain.manage.model.vo.GatewayDistributionVO;
import center.domain.manage.model.vo.GatewayServerDetailVO;
import center.domain.manage.model.vo.GatewayServerVO;
import center.domain.message.Publisher;
import center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import center.domain.register.model.vo.ApplicationInterfaceVO;
import center.domain.register.model.vo.ApplicationSystemVO;
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

    @Resource
    private IRegisterManageService registerManageService;

    @Resource
    private Publisher publisher;


    @Test
    public void test_queryGatewayServerList() {
        List<GatewayServerVO> gatewayServerVOS = configManageService.queryGatewayServerList();
        log.info("Test result: {}", JSON.toJSONString(gatewayServerVOS));
    }

    @Test
    public void test_queryGatewayServerDetailList() {
        List<GatewayServerDetailVO> gatewayServerVOS = configManageService.queryGatewayServerDetailList();
        log.info("Test result: {}", JSON.toJSONString(gatewayServerVOS));
    }

    @Test
    public void test_queryGatewayDistributionList() {
        List<GatewayDistributionVO> gatewayDistributionVOList = configManageService.queryGatewayDistributionList();
        log.info("Test result: {}", JSON.toJSONString(gatewayDistributionVOList));
    }

    @Test
    public void test_application() {
        log.info("Test result: {}", JSON.toJSONString(configManageService.queryApplicationSystemList()));
        log.info("Test result: {}", JSON.toJSONString(configManageService.queryApplicationInterfaceList()));
        log.info("Test result: {}", JSON.toJSONString(configManageService.queryApplicationInterfaceMethodList()));
    }



    @Test
    public void test_registerGatewayServerNode() {
        configManageService.registerGatewayServerNode("10001", "api-gateway-g1", "payment gateway", "127.0.0.196");
        configManageService.registerGatewayServerNode("10001", "api-gateway-g2", "payment gateway", "127.0.0.197");
        configManageService.registerGatewayServerNode("10001", "api-gateway-g3", "delivery gateway", "127.0.0.198");
    }

    @Test
    public void test_registerApplication() {
        ApplicationSystemVO applicationSystemVO = ApplicationSystemVO.builder()
                .systemId("api-gateway-test")
                .systemName("gateway test system")
                .systemType("RPC")
                .systemRegistry("127.0.0.1")
                .build();
        registerManageService.registerApplication(applicationSystemVO);
    }

    @Test
    public void test_registerApplicationInterface() {
        ApplicationInterfaceVO applicationInterfaceVO = ApplicationInterfaceVO.builder()
                .systemId("api-gateway-test")
                .interfaceId("gateway.rpc.IActivityBooth")
                .interfaceName("activity platform")
                .interfaceVersion("1.0.0")
                .build();
        registerManageService.registerApplicationInterface(applicationInterfaceVO);
    }

    @Test
    public void test_registerApplicationInterfaceMethod() {
        ApplicationInterfaceMethodVO applicationInterfaceVO01 = ApplicationInterfaceMethodVO.builder()
                .systemId("api-gateway-test")
                .interfaceId("gateway.rpc.IActivityBooth")
                .methodId("sayHi")
                .methodName("Test Method")
                .parameterType("java.lang.String")
                .uri("/wg/activity/sayHi")
                .httpCommandType("GET")
                .auth(0)
                .build();
        registerManageService.registerApplicationInterfaceMethod(applicationInterfaceVO01);

        ApplicationInterfaceMethodVO applicationInterfaceVO02 = ApplicationInterfaceMethodVO.builder()
                .systemId("api-gateway-test")
                .interfaceId("gateway.rpc.IActivityBooth")
                .methodId("insert")
                .methodName("Insert Method")
                .parameterType("gateway.rpc.dto.XReq")
                .uri("/wg/activity/insert")
                .httpCommandType("POST")
                .auth(1)
                .build();
        registerManageService.registerApplicationInterfaceMethod(applicationInterfaceVO02);
    }

    @Test
    public void test_queryApplicationSystemRichInfo(){
        ApplicationSystemRichInfo result = configManageService.queryApplicationSystemRichInfo("api-gateway-g4", "api-gateway-test-provider");
        log.info("Test Result: {}", JSON.toJSONString(result));
    }

    @Test
    public void test_message() throws InterruptedException {
        publisher.pushMessage("api-gateway-g4", "api-gateway-test-provider");
    }
}
