package xy99.core.mybatis.common;

/**
 * Created by hand on 2018/1/1.
 */
import xy99.core.mybatis.common.BaseMapper;
import xy99.core.mybatis.common.ExampleMapper;
import xy99.core.mybatis.common.Marker;
import xy99.core.mybatis.common.RowBoundsMapper;

public interface Mapper<T> extends BaseMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, Marker {
}
