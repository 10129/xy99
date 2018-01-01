package xy99.core.mybatis.common.condition;

import com.hand.hap.mybatis.provider.ConditionProvider;
import org.apache.ibatis.annotations.DeleteProvider;

public interface DeleteByConditionMapper<T> {
    @DeleteProvider(
            type = ConditionProvider.class,
            method = "dynamicSQL"
    )
    int deleteByCondition(Object var1);
}
