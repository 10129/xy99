package xy99.core.mybatis.common.base;

import com.hand.hap.mybatis.common.base.insert.InsertMapper;
import com.hand.hap.mybatis.common.base.insert.InsertSelectiveMapper;

public interface BaseInsertMapper<T> extends InsertMapper<T>, InsertSelectiveMapper<T> {
}
