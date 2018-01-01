package xy99.core.mybatis.common.condition;

import com.hand.hap.mybatis.provider.ConditionProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateByConditionSelectiveMapper<T> {
    @UpdateProvider(
            type = ConditionProvider.class,
            method = "dynamicSQL"
    )
    int updateByConditionSelective(@Param("record") T var1, @Param("example") Object var2);
}
