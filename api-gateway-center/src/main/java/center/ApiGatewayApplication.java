package center;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @projectName: api-gateway
 * @package: PACKAGE_NAME
 * @className: center.ApiGatewayApplication
 * @author: Peregrine Calder
 * @version: 1.0
 */
@SpringBootApplication
@Configurable
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
