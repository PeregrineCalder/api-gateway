package center.interfaces;

import center.application.IConfigManageService;
import center.application.ILoadBalancingService;
import center.application.IMessageService;
import center.domain.docker.model.aggregates.NginxConfig;
import center.domain.docker.model.vo.LocationVO;
import center.domain.docker.model.vo.UpstreamVO;
import center.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import center.domain.manage.model.vo.*;
import center.infrastructure.common.ResponseCode;
import center.infrastructure.common.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Resource
    private ILoadBalancingService loadBalancingService;


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

    @GetMapping(value = "queryServerDetailConfig", produces = "application/json;charset=utf-8")
    public Result<List<GatewayServerDetailVO>> queryServerDetailConfig() {
        try {
            log.info("Search gateway node configuration information");
            List<GatewayServerDetailVO> gatewayServerVOList = configManageService.queryGatewayServerDetailList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("Search gateway node configuration information exception", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @GetMapping(value = "queryGatewayDistributionList", produces = "application/json;charset=utf-8")
    public Result<List<GatewayDistributionVO>> queryGatewayDistributionList() {
        try {
            log.info("Search gateway distribution configuration information");
            List<GatewayDistributionVO> gatewayServerVOList = configManageService.queryGatewayDistributionList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("Search gateway distribution configuration information exception", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "registerGateway", produces = "application/json;charset=utf-8")
    public Result<Boolean> registerGatewayServerNode(@RequestParam String groupId, @RequestParam String gatewayId, @RequestParam String gatewayName, @RequestParam String gatewayAddress) {
        try {
            log.info("Register gateway service node: gatewayId: {} gatewayName: {} gatewayAddress{}", gatewayId, gatewayName, gatewayAddress);
            boolean done = configManageService.registerGatewayServerNode(groupId, gatewayId, gatewayName, gatewayAddress);
            List<GatewayServerDetailVO> gatewayServerDetailVOList = configManageService.queryGatewayServerDetailList();
            Map<String, List<GatewayServerDetailVO>> gatewayServerDetailMap = gatewayServerDetailVOList.stream()
                    .collect(Collectors.groupingBy(GatewayServerDetailVO::getGroupId));
            Set<String> uniqueGroupIdList = gatewayServerDetailMap.keySet();
            List<LocationVO> locationList = new ArrayList<>();
            for (String name : uniqueGroupIdList) {
                locationList.add(new LocationVO("/" + name + "/", "http://" + name + ";"));
            }
            List<UpstreamVO> upstreamList = new ArrayList<>();
            for (String name : uniqueGroupIdList) {
                List<String> servers = gatewayServerDetailMap.get(name).stream()
                        .map(GatewayServerDetailVO::getGatewayAddress)
                        .collect(Collectors.toList());
                upstreamList.add(new UpstreamVO(name, "least_conn;", servers));
            }
            loadBalancingService.updateNginxConfig(new NginxConfig(upstreamList, locationList));
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), done);
        } catch (Exception e) {
            log.error("Register gateway service node exception", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), false);
        }
    }

    @PostMapping(value = "distributionGateway")
    public void distributionGatewayServerNode(@RequestParam String groupId, @RequestParam String gatewayId) {
        // TODO
    }

    @PostMapping(value = "queryApplicationSystemList", produces = "application/json;charset=utf-8")
    public Result<List<ApplicationSystemVO>> queryApplicationSystemList() {
        try {
            log.info("Search application system configuration information");
            List<ApplicationSystemVO> gatewayServerVOList = configManageService.queryApplicationSystemList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("Search application system configuration information exception", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "queryApplicationInterfaceList", produces = "application/json;charset=utf-8")
    public Result<List<ApplicationInterfaceVO>> queryApplicationInterfaceList() {
        try {
            log.info("Search application interface configuration information");
            List<ApplicationInterfaceVO> gatewayServerVOList = configManageService.queryApplicationInterfaceList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("Search application interface configuration information exception", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "queryApplicationInterfaceMethodList", produces = "application/json;charset=utf-8")
    public Result<List<ApplicationInterfaceMethodVO>> queryApplicationInterfaceMethodList() {
        try {
            log.info("Search application interface method configuration information");
            List<ApplicationInterfaceMethodVO> gatewayServerVOList = configManageService.queryApplicationInterfaceMethodList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("Search application interface method configuration information exception", e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), null);
        }
    }


    @PostMapping(value = "queryApplicationSystemRichInfo", produces = "application/json;charset=utf-8")
    public Result<ApplicationSystemRichInfo> queryApplicationSystemRichInfo(@RequestParam String gatewayId,
                                                                            @RequestParam String systemId) {
        try {
            log.info("Query application system info(system, interface, method) under gateway to be assigned: gatewayId：{}", gatewayId);
            ApplicationSystemRichInfo applicationSystemRichInfo = configManageService.queryApplicationSystemRichInfo(gatewayId, systemId);
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
