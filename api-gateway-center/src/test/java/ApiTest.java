import center.application.IApiService;
import center.domain.model.ApiData;
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
    private IApiService apiService;

    @Test
    public void test() {
        List<ApiData> apiDataList = apiService.queryHttpStatementList();
        log.info("Test result: {}", JSON.toJSONString(apiDataList));
    }
}
