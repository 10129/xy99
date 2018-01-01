package xy99.core.mybatis.common.query;

import xy99.core.mybatis.common.query.JoinOn;
import xy99.core.mybatis.common.query.JoinTables;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.persistence.criteria.JoinType;

@Repeatable(JoinTables.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable {
    String name();

    Class<?> target();

    JoinType type() default JoinType.INNER;

    JoinOn[] on() default {};

    boolean joinMultiLanguageTable() default false;
}
