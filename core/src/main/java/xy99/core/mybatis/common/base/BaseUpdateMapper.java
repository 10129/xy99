package xy99.core.mybatis.common.base;

import xy99.core.mybatis.common.base.update.UpdateByPrimaryKeyMapper;
import xy99.core.mybatis.common.base.update.UpdateByPrimaryKeyOptionsMapper;
import xy99.core.mybatis.common.base.update.UpdateByPrimaryKeySelectiveMapper;

public interface BaseUpdateMapper<T> extends UpdateByPrimaryKeyMapper<T>, UpdateByPrimaryKeySelectiveMapper<T>, UpdateByPrimaryKeyOptionsMapper<T> {
}