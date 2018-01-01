package xy99.core.mybatis.common.base.select;

import com.hand.hap.mybatis.provider.base.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectOneMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    T selectOne(T var1);
}
