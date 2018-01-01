package xy99.core.mybatis.common.base;

import xy99.core.mybatis.common.base.insert.InsertMapper;
import xy99.core.mybatis.common.base.insert.InsertSelectiveMapper;

public interface BaseInsertMapper<T> extends InsertMapper<T>, InsertSelectiveMapper<T> {
}
