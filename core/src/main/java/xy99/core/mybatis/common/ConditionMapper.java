package xy99.core.mybatis.common;

import xy99.core.mybatis.common.condition.DeleteByConditionMapper;
import xy99.core.mybatis.common.condition.SelectByConditionMapper;
import xy99.core.mybatis.common.condition.SelectCountByConditionMapper;
import xy99.core.mybatis.common.condition.UpdateByConditionMapper;
import xy99.core.mybatis.common.condition.UpdateByConditionSelectiveMapper;

public interface ConditionMapper<T> extends SelectByConditionMapper<T>, SelectCountByConditionMapper<T>, DeleteByConditionMapper<T>, UpdateByConditionMapper<T>, UpdateByConditionSelectiveMapper<T> {
}
