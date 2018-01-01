package xy99.core.mybatis.common.base.select;

import xy99.core.mybatis.provider.base.BaseSelectProvider;
import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    List<T> select(T var1);
}
