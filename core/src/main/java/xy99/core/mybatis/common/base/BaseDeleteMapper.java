package xy99.core.mybatis.common.base;

import xy99.core.mybatis.common.base.delete.DeleteByPrimaryKeyMapper;
import xy99.core.mybatis.common.base.delete.DeleteMapper;

public interface BaseDeleteMapper<T> extends DeleteMapper<T>, DeleteByPrimaryKeyMapper<T> {
}
