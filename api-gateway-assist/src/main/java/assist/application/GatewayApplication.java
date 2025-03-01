package assist.application;

import assist.config.GatewayServiceProperties;
import assist.domain.model.aggregates.ApplicationSystemRichInfo;
import assist.domain.model.vo.ApplicationInterfaceMethodVO;
import assist.domain.model.vo.ApplicationInterfaceVO;
import assist.domain.model.vo.ApplicationSystemVO;
import assist.domain.service.GatewayCenterService;
import core.mapping.HttpCommandType;
import core.mapping.HttpStatement;
import core.session.Configuration;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.List;

/**
 * @projectName: api-gateway
 * @package: assist.application
 * @className: GatewayApplication
 * @author: Peregrine Calder
 * @version: 1.0
 */
@AllArgsConstructor
@Slf4j
public class GatewayApplication implements ApplicationContextAware, ApplicationListener<ContextClosedEvent> {

    private GatewayServiceProperties properties;
    private GatewayCenterService gatewayCenterService;
    private Configuration configuration;
    private Channel gatewaySocketServerChannel;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try {
            if (gatewaySocketServerChannel.isActive()) {
                log.info("Application container closed. API Service closed. localAddress：{}", gatewaySocketServerChannel.localAddress());
                gatewaySocketServerChannel.close();
            }
        } catch (Exception e) {
            log.error("Application container closed. API Service fail to close.", e);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            gatewayCenterService.doRegister(properties.getAddress(),
                    properties.getGroupId(),
                    properties.getGatewayId(),
                    properties.getGatewayName(),
                    properties.getGatewayAddress());

            ApplicationSystemRichInfo applicationSystemRichInfo = gatewayCenterService.pullApplicationSystemRichInfo(properties.getAddress(), properties.getGatewayId());
            List<ApplicationSystemVO> applicationSystemVOList = applicationSystemRichInfo.getApplicationSystemVOList();
            for (ApplicationSystemVO applicationSystemVO : applicationSystemVOList) {
                List<ApplicationInterfaceVO> interfaceVOList = applicationSystemVO.getInterfaceList();
                for (ApplicationInterfaceVO applicationInterfaceVO : interfaceVOList) {
                    configuration.registryConfig(applicationSystemVO.getSystemId(), applicationSystemVO.getSystemRegistry(), applicationInterfaceVO.getInterfaceId(), applicationInterfaceVO.getInterfaceVersion());
                    List<ApplicationInterfaceMethodVO> methodVOList = applicationInterfaceVO.getMethodList();
                    for (ApplicationInterfaceMethodVO applicationInterfaceMethodVO : methodVOList) {
                        HttpStatement httpStatement = new HttpStatement(
                                applicationSystemVO.getSystemId(),
                                applicationInterfaceVO.getInterfaceId(),
                                applicationInterfaceMethodVO.getMethodId(),
                                applicationInterfaceMethodVO.getParameterType(),
                                applicationInterfaceMethodVO.getUri(),
                                HttpCommandType.valueOf(applicationInterfaceMethodVO.getHttpCommandType()),
                                applicationInterfaceMethodVO.isAuth()
                        );
                        configuration.addMapper(httpStatement);
                        log.info("Gateway Register Service Mapping: System: {} Interface: {} Method: {}", applicationSystemVO.getSystemId(), applicationInterfaceMethodVO.getInterfaceId(), applicationInterfaceMethodVO.getMethodId());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Gateway Service fail to start. Stop Service. {}", e.getMessage(), e);
            throw e;
        }
    }
}
