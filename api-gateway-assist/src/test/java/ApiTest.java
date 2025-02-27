import assist.common.Result;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: api-gateway
 * @package: PACKAGE_NAME
 * @className: ApiTest
 * @author: Peregrine Calder
 * @version: 1.0
 */
public class ApiTest {
    @Test
    public void test_register_gateway() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", "10001");
        paramMap.put("gatewayId", "api-gateway-g4");
        paramMap.put("gatewayName", "delivery gateway");
        paramMap.put("gatewayAddress", "127.0.0.1");

        String resultStr = HttpUtil.post("http://localhost:8001/wg/admin/config/registerGateway", paramMap, 350);
        System.out.println(resultStr);

        Result result = JSON.parseObject(resultStr, Result.class);
        System.out.println(result.getCode());
    }
}
