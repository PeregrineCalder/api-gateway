package sdk.application;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import sdk.GatewayException;
import sdk.annotation.ApiProducerClazz;
import sdk.annotation.ApiProducerMethod;
import sdk.config.GatewaySDKServiceProperties;
import sdk.domain.service.GatewayCenterService;

import java.lang.reflect.Method;

/**
 * @projectName: api-gateway
 * @package: sdk.application
 * @className: GatewaySDKApplication
 * @author: Peregrine Calder
 * @version: 1.0
 */
@Slf4j
@AllArgsConstructor
public class GatewaySDKApplication implements BeanPostProcessor {

    private GatewaySDKServiceProperties properties;
    private GatewayCenterService gatewayCenterService;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ApiProducerClazz apiProducerClazz = bean.getClass().getAnnotation(ApiProducerClazz.class);
        if (apiProducerClazz != null) {
            // System information
            log.info("\nApplication Registration: System information \nsystemId: {} \nsystemName: {} \nsystemType: {} \nsystemRegistry: {}", properties.getSystemId(), properties.getSystemName(), "RPC", properties.getSystemRegistry());
            gatewayCenterService.doRegisterApplication(properties.getAddress(), properties.getSystemId(), properties.getSystemName(), "RPC", properties.getSystemRegistry());

            // Interface information
            Class<?>[] interfaces = bean.getClass().getInterfaces();
            if (interfaces.length != 1) {
                throw new GatewayException(bean.getClass().getName() + "interfaces not one this is " + JSON.toJSONString(interfaces));
            }
            String interfaceId = interfaces[0].getName();
            log.info("\nApplication Registration: Interface information \nsystemId: {} \ninterfaceId: {} \ninterfaceName: {} \ninterfaceVersion: {}", properties.getSystemId(), bean.getClass().getName(), apiProducerClazz.interfaceName(), apiProducerClazz.interfaceVersion());
            gatewayCenterService.doRegisterApplicationInterface(properties.getAddress(),
                    properties.getSystemId(),
                    interfaceId,
                    apiProducerClazz.interfaceName(),
                    apiProducerClazz.interfaceVersion()
            );

            // Method information
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                ApiProducerMethod apiProducerMethod = method.getAnnotation(ApiProducerMethod.class);
                if (apiProducerMethod != null) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    StringBuilder parameters = new StringBuilder();
                    for (Class<?> clazz : parameterTypes) {
                        parameters.append(clazz.getName()).append(",");
                    }
                    String parameterType = parameters.toString().substring(0, parameters.toString().lastIndexOf(","));
                    log.info("\nApplication Registration: Method Information \nsystemId: {} \ninterfaceId: {} \nmethodId: {} \nmethodName: {} \nparameterType: {} \nuri: {} \nhttpCommandType: {} \nauth: {}",
                            properties.getSystemId(),
                            bean.getClass().getName(),
                            method.getName(),
                            apiProducerMethod.methodName(),
                            parameterType,
                            apiProducerMethod.uri(),
                            apiProducerMethod.httpCommandType(),
                            apiProducerMethod.auth());
                    gatewayCenterService.doRegisterApplicationInterfaceMethod(properties.getAddress(),
                            properties.getSystemId(),
                            interfaceId,
                            method.getName(),
                            apiProducerMethod.methodName(),
                            parameterType,
                            apiProducerMethod.uri(),
                            apiProducerMethod.httpCommandType(),
                            apiProducerMethod.auth());
                }
            }
        }

        gatewayCenterService.doRegisterEvent(properties.getAddress(), properties.getSystemId());

        return bean;
    }
}
