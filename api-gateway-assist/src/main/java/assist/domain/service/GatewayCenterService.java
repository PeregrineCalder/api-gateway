package assist.domain.service;

import assist.GatewayException;
import assist.common.Result;
import assist.domain.model.aggregates.ApplicationSystemRichInfo;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


/**
 * @projectName: api-gateway
 * @package: assist.service
 * @className: RegisterGatewayService
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
public class GatewayCenterService {

    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);
        paramMap.put("gatewayId", gatewayId);
        paramMap.put("gatewayName", gatewayName);
        paramMap.put("gatewayAddress", gatewayAddress);
        String resultStr;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/config/registerGateway", paramMap, 550);
        } catch (Exception e) {
            log.error("abnormal gateway service registration, unavailable link resources：{}", address + "/wg/admin/config/registerGateway");
            throw e;
        }
        Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<>() {});
        log.info("Register gateway service on gateway center gatewayId: {} gatewayName: {} gatewayAddress: {} register result: {}", gatewayId, gatewayName, gatewayAddress, resultStr);
        if (!"0000".equals(result.getCode()))
            throw new GatewayException("Gateway Service Registration Anomaly [gatewayId：" + gatewayId + "] 、[gatewayAddress：" + gatewayAddress + "]");
    }

    public ApplicationSystemRichInfo pullApplicationSystemRichInfo(String address, String gatewayId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gatewayId", gatewayId);
        String resultStr;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/config/queryApplicationSystemRichInfo", paramMap, 550);
        } catch (Exception e) {
            log.error("Abnormal gateway service pull, unavailable link resources：{}", address + "/wg/admin/config/queryApplicationSystemRichInfo");
            throw e;
        }
        Result<ApplicationSystemRichInfo> result = JSON.parseObject(resultStr, new TypeReference<>(){});
        log.info("Pull the configuration information of the application service and interface from the gateway center to the local device for registration: gatewayId：{}", gatewayId);
        if (!"0000".equals(result.getCode()))
            throw new GatewayException("Pull the configuration information of the application service and interface from the gateway center to the local device for registration error [gatewayId：" + gatewayId + "]");
        return result.getData();
    }
}
