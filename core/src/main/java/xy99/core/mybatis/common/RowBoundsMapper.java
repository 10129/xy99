package xy99.core.mybatis.common;

import xy99.core.mybatis.common.rowbounds.SelectByExampleRowBoundsMapper;
import xy99.core.mybatis.common.rowbounds.SelectRowBoundsMapper;

public interface RowBoundsMapper<T> extends SelectByExampleRowBoundsMapper<T>, SelectRowBoundsMapper<T> {
}
