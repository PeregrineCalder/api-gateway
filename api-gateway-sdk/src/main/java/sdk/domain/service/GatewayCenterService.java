package sdk.domain.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;
import sdk.GatewayException;
import sdk.common.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: sdk.domain.service
 * @className: GatewayCenterService
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
public class GatewayCenterService {
    public void doRegisterApplication(String address, String systemId, String systemName, String systemType, String systemRegistry) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("systemName", systemName);
        paramMap.put("systemType", systemType);
        paramMap.put("systemRegistry", systemRegistry);
        String resultStr;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplication", paramMap, 550);
        } catch (Exception e) {
            log.error("Application service registration exception, link resources unavailable: {}", address + "/wg/admin/register/registerApplication");
            throw e;
        }
        Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<>() {
        });
        log.info("Register application service on gateway: systemId: {} systemName: {} registration result: {}", systemId, systemName, resultStr);
        if (!"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("Application service registration exception [systemId：" + systemId + "] 、[systemRegistry：" + systemRegistry + "]");
    }

    public void doRegisterApplicationInterface(String address, String systemId, String interfaceId, String interfaceName, String interfaceVersion) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("interfaceId", interfaceId);
        paramMap.put("interfaceName", interfaceName);
        paramMap.put("interfaceVersion", interfaceVersion);
        String resultStr;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplicationInterface", paramMap, 550);
        } catch (Exception e) {
            log.error("Application service interface registration exception, link resources unavailable: {}", address + "/wg/admin/register/registerApplicationInterface");
            throw e;
        }
        Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<>() {
        });
        log.info("Register application service interface on gateway: systemId: {} interfaceId: {} interfaceName: {} registration result: {}", systemId, interfaceId, interfaceName, resultStr);
        if (!"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("Application service interface registration exception [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "]");
    }

    public void doRegisterApplicationInterfaceMethod(String address,
                                                     String systemId,
                                                     String interfaceId,
                                                     String methodId,
                                                     String methodName,
                                                     String parameterType,
                                                     String uri,
                                                     String httpCommandType,
                                                     Integer auth) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("interfaceId", interfaceId);
        paramMap.put("methodId", methodId);
        paramMap.put("methodName", methodName);
        paramMap.put("parameterType", parameterType);
        paramMap.put("uri", uri);
        paramMap.put("httpCommandType", httpCommandType);
        paramMap.put("auth", auth);

        String resultStr;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplicationInterfaceMethod", paramMap, 550);
        } catch (Exception e) {
            log.error("Application service interface method registration exception, link resources unavailable: {}", address + "/wg/admin/register/registerApplicationInterfaceMethod");
            throw e;
        }
        Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<>() {
        });
        log.info("Register application service interface method on gateway: systemId: systemId：{} interfaceId：{} methodId：{} 注册结果：{}", systemId, interfaceId, methodId, resultStr);
        if (!"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("Application service interface method registration exception [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "]、[methodId：]" + methodId + "]");
    }

    public void doRegisterEvent(String address, String systemId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        String resultStr;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerEvent", paramMap, 550);
        } catch (Exception e) {
            log.error("Application service interface method exception, link resources unavailable: {}", address + "/wg/admin/register/registerEvent");
            throw e;
        }
        Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<>() {
        });
        log.info("application service interface event method: systemId：{} registration result: {}", systemId, resultStr);
        if (!"0000".equals(result.getCode()))
            throw new GatewayException("Register application service interface on gateway: [systemId：" + systemId + "] ");
    }
}
