package sdk.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiProducerMethod {

    String methodName() default "";

    String uri() default "";

    String httpCommandType() default "GET";

    int auth() default 0;
}
