package center.interfaces;

import center.application.IApiService;
import center.domain.model.ApiData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: interfaces
 * @className: ApiGatewayController
 * @author: Peregrine Calder
 * @version: 1.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ApiGatewayController {
    @Resource
    private IApiService apiService;

    @GetMapping(value = "list", produces = "center/application/json;charset=utf-8")
    public List<ApiData> getAnswerMap(){
        return apiService.queryHttpStatementList();
    }
}
