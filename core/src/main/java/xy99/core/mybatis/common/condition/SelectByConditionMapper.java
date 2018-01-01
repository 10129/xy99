package xy99.core.mybatis.common.condition;

import com.hand.hap.mybatis.provider.ConditionProvider;
import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectByConditionMapper<T> {
    @SelectProvider(
            type = ConditionProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByCondition(Object var1);
}
