package xy99.core.mybatis.common.condition;

import xy99.core.mybatis.provider.ConditionProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateByConditionMapper<T> {
    @UpdateProvider(
            type = ConditionProvider.class,
            method = "dynamicSQL"
    )
    int updateByCondition(@Param("record") T var1, @Param("example") Object var2);
}
