package xy99.core.mybatis.common.base;

import com.hand.hap.mybatis.common.base.update.UpdateByPrimaryKeyMapper;
import com.hand.hap.mybatis.common.base.update.UpdateByPrimaryKeyOptionsMapper;
import com.hand.hap.mybatis.common.base.update.UpdateByPrimaryKeySelectiveMapper;

public interface BaseUpdateMapper<T> extends UpdateByPrimaryKeyMapper<T>, UpdateByPrimaryKeySelectiveMapper<T>, UpdateByPrimaryKeyOptionsMapper<T> {
}