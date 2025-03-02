package sdk.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})

public @interface ApiProducerClazz {
    String interfaceName() default "";

    String interfaceVersion() default "";
}
