package xy99.core.mybatis.common.base.update;

import xy99.core.mybatis.provider.base.BaseUpdateProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateByPrimaryKeyMapper<T> {
    @UpdateProvider(
            type = BaseUpdateProvider.class,
            method = "dynamicSQL"
    )
    int updateByPrimaryKey(T var1);
}
