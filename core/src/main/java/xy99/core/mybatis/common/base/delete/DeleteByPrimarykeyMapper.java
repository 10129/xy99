package xy99.core.mybatis.common.base.delete;

import xy99.core.mybatis.provider.base.BaseDeleteProvider;
import org.apache.ibatis.annotations.DeleteProvider;

public interface DeleteByPrimarykeyMapper<T> {
    @DeleteProvider(
            type = BaseDeleteProvider.class,
            method = "dynamicSQL"
    )
    int deleteByPrimaryKey(Object var1);
}
