package center.interfaces;

import center.application.IDataOperationManageService;
import center.domain.operation.model.vo.*;
import center.infrastructure.common.OperationRequest;
import center.infrastructure.common.OperationResult;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: api-gateway
 * @package: center.interfaces
 * @className: DataOperationManage
 * @author: Peregrine Calder
 * @version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("/wg/admin/data")
@Slf4j
public class DataOperationManage {
    @Resource
    private IDataOperationManageService dataOperationManageService;

    @GetMapping(value = "queryGatewayServer", produces = "application/json;charset=utf-8")
    public OperationResult<GatewayServerDataVO> queryGatewayServer(@RequestParam String groupId,
                                                                   @RequestParam String page,
                                                                   @RequestParam String limit) {
        try {
            log.info("Start searching gateway service data: groupId：{} page：{} limit：{}", groupId, page, limit);
            OperationRequest<String> req = new OperationRequest<>(page, limit);
            req.setData(groupId);
            OperationResult<GatewayServerDataVO> operationResult = dataOperationManageService.queryGatewayServer(req);
            log.info("Finish searching gateway service data: operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("Search gateway service data exception: groupId：{}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryGatewayServerDetail", produces = "application/json;charset=utf-8")
    public OperationResult<GatewayServerDetailDataVO> queryGatewayServerDetail(@RequestParam String groupId,
                                                                               @RequestParam String gatewayId,
                                                                               @RequestParam String page,
                                                                               @RequestParam String limit) {
        try {
            log.info("Start searching gateway service detail data: groupId：{} gatewayId：{} page：{} limit：{}", groupId, gatewayId, page, limit);
            OperationRequest<GatewayServerDetailDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new GatewayServerDetailDataVO(groupId, gatewayId));
            OperationResult<GatewayServerDetailDataVO> operationResult = dataOperationManageService.queryGatewayServerDetail(req);
            log.info("Finish searching gateway service detail data: operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("Search gateway service data exception: groupId：{}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryGatewayDistribution", produces = "application/json;charset=utf-8")
    public OperationResult<GatewayDistributionDataVO> queryGatewayDistribution(@RequestParam String groupId,
                                                                               @RequestParam String gatewayId,
                                                                               @RequestParam String page,
                                                                               @RequestParam String limit) {
        try {
            log.info("Start searching gateway distribution data: groupId：{} gatewayId：{} page：{} limit：{}", groupId, gatewayId, page, limit);
            OperationRequest<GatewayDistributionDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new GatewayDistributionDataVO(groupId, gatewayId));
            OperationResult<GatewayDistributionDataVO> operationResult = dataOperationManageService.queryGatewayDistribution(req);
            log.info("Finish searching gateway distribution data: operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("Search gateway distribution data exception: groupId：{}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationSystem", produces = "application/json;charset=utf-8")
    public OperationResult<ApplicationSystemDataVO> queryApplicationSystem(@RequestParam String systemId,
                                                                           @RequestParam String systemName,
                                                                           @RequestParam String page,
                                                                           @RequestParam String limit) {
        try {
            log.info("Start searching application system: systemId：{} systemName：{} page：{} limit：{}", systemId, systemName, page, limit);
            OperationRequest<ApplicationSystemDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new ApplicationSystemDataVO(systemId, systemName));
            OperationResult<ApplicationSystemDataVO> operationResult = dataOperationManageService.queryApplicationSystem(req);
            log.info("Finish searching application system: operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("Search application system exception: systemId：{} systemName：{}", systemId, systemId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationInterface", produces = "application/json;charset=utf-8")
    public OperationResult<ApplicationInterfaceDataVO> queryApplicationInterface(@RequestParam String systemId,
                                                                                 @RequestParam String interfaceId,
                                                                                 @RequestParam String page,
                                                                                 @RequestParam String limit) {
        try {
            log.info("Start searching application interface: systemId：{} interfaceId：{} page：{} limit：{}", systemId, interfaceId, page, limit);
            OperationRequest<ApplicationInterfaceDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new ApplicationInterfaceDataVO(systemId, interfaceId));
            OperationResult<ApplicationInterfaceDataVO> operationResult = dataOperationManageService.queryApplicationInterface(req);
            log.info("Finish searching application interface: operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("Search application interface exception: systemId：{} interfaceId：{}", systemId, interfaceId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationInterfaceMethodList", produces = "application/json;charset=utf-8")
    public OperationResult<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethodList(@RequestParam String systemId,
                                                                                                 @RequestParam String interfaceId,
                                                                                                 @RequestParam String page,
                                                                                                 @RequestParam String limit) {
        try {
            log.info("Start searching application interface method: systemId：{} interfaceId：{} page：{} limit：{}", systemId, interfaceId, page, limit);
            OperationRequest<ApplicationInterfaceMethodDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new ApplicationInterfaceMethodDataVO(systemId, interfaceId));
            OperationResult<ApplicationInterfaceMethodDataVO> operationResult = dataOperationManageService.queryApplicationInterfaceMethod(req);
            log.info("Finish searching application interface method: operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("Search application interface method exception: systemId：{} interfaceId：{}", systemId, interfaceId, e);
            return new OperationResult<>(0, null);
        }
    }

}
