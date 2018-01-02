package xy99.core.mybatis.common.base;

import xy99.core.mybatis.common.base.delete.DeleteByPrimarykeyMapper;
import xy99.core.mybatis.common.base.delete.DeleteMapper;

public interface BaseDeleteMapper<T> extends DeleteMapper<T>, DeleteByPrimarykeyMapper<T> {
}
