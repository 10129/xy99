package xy99.core.mybatis.common.condition;

import com.hand.hap.mybatis.provider.ConditionProvider;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectCountByConditionMapper<T> {
    @SelectProvider(
            type = ConditionProvider.class,
            method = "dynamicSQL"
    )
    int selectCountByCondition(Object var1);
}
