package sdk.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import sdk.annotation.ApiProducerClazz;
import sdk.annotation.ApiProducerMethod;
import sdk.config.GatewaySDKServiceProperties;

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

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        ApiProducerClazz apiProducerClazz = bean.getClass().getAnnotation(ApiProducerClazz.class);
        if (apiProducerClazz != null) {
            log.info("\nApplication Registration: System information \nsystemId: {} \nsystemName: {} \nsystemType: {} \nsystemRegistry: {}", properties.getSystemId(), properties.getSystemName(), "RPC", properties.getSystemRegistry());
            log.info("\nApplication Registration: Interface information \nsystemId: {} \ninterfaceId: {} \ninterfaceName: {} \ninterfaceVersion: {}", properties.getSystemId(), bean.getClass().getName(), apiProducerClazz.interfaceName(), apiProducerClazz.interfaceVersion());
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
                }
            }
        }
        return bean;
    }
}
