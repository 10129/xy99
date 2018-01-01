package xy99.core.mybatis.common;

import com.hand.hap.mybatis.common.rowbounds.SelectByExampleRowBoundsMapper;
import com.hand.hap.mybatis.common.rowbounds.SelectRowBoundsMapper;

public interface RowBoundsMapper<T> extends SelectByExampleRowBoundsMapper<T>, SelectRowBoundsMapper<T> {
}
