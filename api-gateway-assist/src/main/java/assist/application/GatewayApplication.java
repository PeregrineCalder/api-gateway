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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

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
public class GatewayApplication implements ApplicationListener<ContextRefreshedEvent> {

    private GatewayServiceProperties properties;
    private GatewayCenterService gatewayCenterService;
    private Configuration configuration;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        gatewayCenterService.doRegister(properties.getAddress(),
                properties.getGroupId(),
                properties.getGatewayId(),
                properties.getGatewayName(),
                properties.getGatewayAddress()
                );

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
                    log.info("gateway service registration mapping: system:{}, interface:{}, method:{}", applicationSystemVO.getSystemId(), applicationInterfaceVO.getInterfaceId(), applicationInterfaceMethodVO.getMethodId());
                }
            }
        }
    }
}
