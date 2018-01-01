package xy99.core.mybatis.common;

import com.hand.hap.mybatis.common.condition.DeleteByConditionMapper;
import com.hand.hap.mybatis.common.condition.SelectByConditionMapper;
import com.hand.hap.mybatis.common.condition.SelectCountByConditionMapper;
import com.hand.hap.mybatis.common.condition.UpdateByConditionMapper;
import com.hand.hap.mybatis.common.condition.UpdateByConditionSelectiveMapper;

public interface ConditionMapper<T> extends SelectByConditionMapper<T>, SelectCountByConditionMapper<T>, DeleteByConditionMapper<T>, UpdateByConditionMapper<T>, UpdateByConditionSelectiveMapper<T> {
}
