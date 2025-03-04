package center.interfaces;

import center.application.IConfigManageService;
import center.application.IMessageService;
import center.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import center.domain.manage.model.vo.GatewayServerVO;
import center.infrastructure.common.ResponseCode;
import center.infrastructure.common.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: center.interfaces
 * @className: GatewayConfigManage
 * @author: Peregrine Calder
 * @description: Gateway registration, Service grouping, Service association
 * @version: 1.0
 */
@RestController
@RequestMapping("/wg/admin/config")
@Slf4j
public class GatewayConfigManage {
    @Resource
    private IConfigManageService configManageService;
    @Resource
    private IMessageService messageService;

    @GetMapping(value = "queryServerConfig", produces = "application/json;charset=utf-8")
    public Result<List<GatewayServerVO>> queryServerConfig() {
        try {
            log.info("Query Gateway Service Configuration Information");
            List<GatewayServerVO> gatewayServerVOList = configManageService.queryGatewayServerList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("Query Gateway Service Configuration Error", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "registerGateway")
    public Result<Boolean> registerGatewayServerNode(@RequestParam String groupId, @RequestParam String gatewayId, @RequestParam String gatewayName, @RequestParam String gatewayAddress) {
        try {
            log.info("Register Gateway Server Node gatewayId：{} gatewayName：{} gatewayAddress：{}", gatewayId, gatewayName, gatewayAddress);
            boolean done = configManageService.registerGatewayServerNode(groupId, gatewayId, gatewayName, gatewayAddress);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), done);
        } catch (Exception e) {
            log.error("Register Gateway Server Node Error", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "distributionGateway")
    public void distributionGatewayServerNode(@RequestParam String groupId, @RequestParam String gatewayId) {
        // TODO
    }

    @PostMapping(value = "queryApplicationSystemRichInfo", produces = "application/json;charset=utf-8")
    public Result<ApplicationSystemRichInfo> queryApplicationSystemRichInfo(@RequestParam String gatewayId) {
        try {
            log.info("Query application system info(system, interface, method) under gateway to be assigned: gatewayId：{}", gatewayId);
            ApplicationSystemRichInfo applicationSystemRichInfo = configManageService.queryApplicationSystemRichInfo(gatewayId);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), applicationSystemRichInfo);
        } catch (Exception e) {
            log.error("Fail to query application system info(system, interface, method) under gateway to be assigned: gatewayId gatewayId：{}", gatewayId, e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "queryRedisConfig", produces = "application/json;charset=utf-8")
    public Result<Map<String, String>> queryRedisConfig() {
        try {
            log.info("Find gateway center redis configuration information");
            Map<String, String> redisConfig = messageService.queryRedisConfig();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), redisConfig);
        } catch (Exception e) {
            log.error("Fail to find gateway center redis configuration information", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

}
