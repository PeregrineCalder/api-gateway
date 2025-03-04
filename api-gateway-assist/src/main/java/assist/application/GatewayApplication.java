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
            addMappers("");
        } catch (Exception e) {
            log.error("Gateway Service fail to start. Stop Service. {}", e.getMessage(), e);
            throw e;
        }
    }

    public void addMappers(String systemId) {
        ApplicationSystemRichInfo applicationSystemRichInfo = gatewayCenterService.pullApplicationSystemRichInfo(properties.getAddress(), properties.getGatewayId(), systemId);
        List<ApplicationSystemVO> applicationSystemVOList = applicationSystemRichInfo.getApplicationSystemVOList();
        if (applicationSystemVOList.isEmpty()) {
            log.warn("Gateway {} Service registration mapping fails, check gatewayCenterService.pullApplicationSystemRichInfo whether configuration data needs to be pulled for this gateway is retrieved", systemId);
            return;
        }
        for (ApplicationSystemVO system : applicationSystemVOList) {
            List<ApplicationInterfaceVO> interfaceList = system.getInterfaceList();
            for (ApplicationInterfaceVO itf : interfaceList) {
                // 2.1 创建配置信息加载注册
                configuration.registryConfig(system.getSystemId(), system.getSystemRegistry(), itf.getInterfaceId(), itf.getInterfaceVersion());
                List<ApplicationInterfaceMethodVO> methodList = itf.getMethodList();
                // 2.2 注册系统服务接口信息
                for (ApplicationInterfaceMethodVO method : methodList) {
                    HttpStatement httpStatement = new HttpStatement(
                            system.getSystemId(),
                            itf.getInterfaceId(),
                            method.getMethodId(),
                            method.getParameterType(),
                            method.getUri(),
                            HttpCommandType.valueOf(method.getHttpCommandType()),
                            method.isAuth());
                    configuration.addMapper(httpStatement);
                    log.info("Gateway Register Service Mapping: System: {} Interface: {} Method: {}", system.getSystemId(), itf.getInterfaceId(), method.getMethodId());
                }
            }
        }
    }

    public void receiveMessage(Object message) {
        log.info("[Event Notification] Receive registration center push message: {}", message);
        addMappers(message.toString().substring(1, message.toString().length() - 1));
    }
    

}
