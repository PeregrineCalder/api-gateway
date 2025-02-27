package assist.service;

import assist.GatewayException;
import assist.common.Result;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
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
public class RegisterGatewayService {

    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);
        paramMap.put("gatewayId", gatewayId);
        paramMap.put("gatewayName", gatewayName);
        paramMap.put("gatewayAddress", gatewayAddress);
        String resultStr = HttpUtil.post(address, paramMap, 350);
        Result result = JSON.parseObject(resultStr, Result.class);
        log.info("Register gateway service on gateway center gatewayId: {} gatewayName: {} gatewayAddress: {} register result: {}", gatewayId, gatewayName, gatewayAddress, resultStr);
        if (!"0000".equals(result.getCode()))
            throw new GatewayException("Gateway Service Registration Anomaly [gatewayId：" + gatewayId + "] 、[gatewayAddress：" + gatewayAddress + "]");
    }
}
