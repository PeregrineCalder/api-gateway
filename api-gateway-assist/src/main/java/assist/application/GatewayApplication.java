package assist.application;

import assist.config.GatewayServiceProperties;
import assist.domain.service.GatewayCenterService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @projectName: api-gateway
 * @package: assist.application
 * @className: GatewayApplication
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
public class GatewayApplication implements ApplicationListener<ContextRefreshedEvent> {

    private GatewayServiceProperties properties;
    private GatewayCenterService registerGatewayService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        registerGatewayService.doRegister(properties.getAddress(),
                properties.getGroupId(),
                properties.getGatewayId(),
                properties.getGatewayName(),
                properties.getGatewayAddress()
                );
    }
}
