package xy99.core.mybatis.common.base.select;

import com.hand.hap.mybatis.provider.base.BaseSelectProvider;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectByPrimaryKeyMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    T selectByPrimaryKey(Object var1);
}
