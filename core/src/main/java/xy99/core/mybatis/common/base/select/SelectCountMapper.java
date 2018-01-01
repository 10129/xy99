package xy99.core.mybatis.common.base.select;

import xy99.core.mybatis.provider.base.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectCountMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    int selectCount(T var1);
}
