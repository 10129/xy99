package xy99.core.mybatis.annotation;


import xy99.core.mybatis.code.Style;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameStyle {
    Style value() default Style.normal;
}
