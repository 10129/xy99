package xy99.core.mybatis.common.query;

/**
 * Created by hand on 2018/1/1.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinCache {
    String joinKey();

    String cacheName();

    String joinColumn();
}
