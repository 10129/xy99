package xy99.core.mybatis.common.sqlserver;

/**
 * Created by hand on 2018/1/1.
 */
import xy99.core.mybatis.provider.SqlServerProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

public interface InsertSelectiveMapper<T> {
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id"
    )
    @InsertProvider(
            type = SqlServerProvider.class,
            method = "dynamicSQL"
    )
    int insertSelective(T var1);
}
