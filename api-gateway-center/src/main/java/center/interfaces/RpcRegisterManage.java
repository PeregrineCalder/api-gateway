package center.interfaces;

import center.application.IRegisterManageService;
import center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import center.domain.register.model.vo.ApplicationInterfaceVO;
import center.domain.register.model.vo.ApplicationSystemVO;
import center.infrastructure.common.ResponseCode;
import center.infrastructure.common.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: api-gateway
 * @package: center.interfaces
 * @className: RpcRegisterManage
 * @author: Peregrine Calder
 * @version: 1.0
 */
@RestController
@RequestMapping("/wg/admin/register")
@Slf4j
public class RpcRegisterManage {
    @Resource
    private IRegisterManageService registerManageService;

    @PostMapping(value = "registerApplication", produces = "application/json;charset=utf-8")
    public Result<Boolean> registerApplication(@RequestParam String systemId,
                                               @RequestParam String systemName,
                                               @RequestParam String systemType,
                                               @RequestParam String systemRegistry) {

        try {
            log.info("Register Application Service: systemId：{}", systemId);
            ApplicationSystemVO applicationSystemVO = ApplicationSystemVO.builder()
                    .systemId(systemId)
                    .systemName(systemName)
                    .systemType(systemType)
                    .systemRegistry(systemRegistry)
                    .build();
            registerManageService.registerApplication(applicationSystemVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        } catch (DuplicateKeyException e) {
            log.warn("Register Application Service Repetition: systemId：{}", systemId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        } catch (Exception e) {
            log.error("Register Application Service Fail: systemId：{}", systemId, e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), false);
        }
    }

    @PostMapping(value = "registerApplicationInterface", produces = "application/json;charset=utf-8")
    public Result<Boolean> registerApplicationInterface(@RequestParam String systemId,
                                                        @RequestParam String interfaceId,
                                                        @RequestParam String interfaceName,
                                                        @RequestParam String interfaceVersion) {
        try {
            log.info("Register Application Interface: systemId：{} interfaceId：{}", systemId, interfaceId);
            ApplicationInterfaceVO applicationInterfaceVO = ApplicationInterfaceVO.builder()
                    .systemId(systemId)
                    .interfaceId(interfaceId)
                    .interfaceName(interfaceName)
                    .interfaceVersion(interfaceVersion)
                    .build();
            registerManageService.registerApplicationInterface(applicationInterfaceVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        } catch (DuplicateKeyException e) {
            log.warn("Register Application Service Repetition: systemId：{} interfaceId：{}", systemId, interfaceId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        } catch (Exception e) {
            log.error("Register Application Service Fail: systemId：{}", systemId, e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), false);
        }
    }

    @PostMapping(value = "registerApplicationInterfaceMethod", produces = "application/json;charset=utf-8")
    public Result<Boolean> registerApplicationInterfaceMethod(@RequestParam String systemId,
                                                              @RequestParam String interfaceId,
                                                              @RequestParam String methodId,
                                                              @RequestParam String methodName,
                                                              @RequestParam String parameterType,
                                                              @RequestParam String uri,
                                                              @RequestParam String httpCommandType,
                                                              @RequestParam Integer auth) {
        try {
            log.info("Register Application Interface Method: systemId：{} interfaceId：{} methodId：{}", systemId, interfaceId, methodId);
            ApplicationInterfaceMethodVO applicationInterfaceVO = ApplicationInterfaceMethodVO.builder()
                    .systemId(systemId)
                    .interfaceId(interfaceId)
                    .methodId(methodId)
                    .methodName(methodName)
                    .parameterType(parameterType)
                    .uri(uri)
                    .httpCommandType(httpCommandType)
                    .auth(auth)
                    .build();
            registerManageService.registerApplicationInterfaceMethod(applicationInterfaceVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        } catch (DuplicateKeyException e) {
            log.warn("Register Application Service Repetition: systemId：{} interfaceId：{}", systemId, interfaceId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        } catch (Exception e) {
            log.error("Register Application Service Fail: systemId：{}", systemId, e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), false);
        }
    }
}
