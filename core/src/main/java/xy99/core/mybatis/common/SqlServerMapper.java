package xy99.core.mybatis.common;

import com.hand.hap.mybatis.common.sqlserver.InsertMapper;
import com.hand.hap.mybatis.common.sqlserver.InsertSelectiveMapper;

public interface SqlServerMapper<T> extends InsertMapper<T>, InsertSelectiveMapper<T> {
}
