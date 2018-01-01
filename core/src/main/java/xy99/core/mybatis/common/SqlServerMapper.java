package xy99.core.mybatis.common;

import xy99.core.mybatis.common.sqlserver.InsertMapper;
import xy99.core.mybatis.common.sqlserver.InsertSelectiveMapper;

public interface SqlServerMapper<T> extends InsertMapper<T>, InsertSelectiveMapper<T> {
}
