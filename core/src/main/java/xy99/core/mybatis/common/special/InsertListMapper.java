package xy99.core.mybatis.common.special;

/**
 * Created by hand on 2018/1/1.
 */
import xy99.core.mybatis.provider.SpecialProvider;
import java.util.List;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

public interface InsertListMapper<T> {
    @Options(
            useGeneratedKeys = true,
            keyProperty = "id"
    )
    @InsertProvider(
            type = SpecialProvider.class,
            method = "dynamicSQL"
    )
    int insertList(List<T> var1);
}