package xy99.core.mybatis.common.query;

import xy99.core.mybatis.common.query.JoinTable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTables {
    JoinTable[] value();
}
