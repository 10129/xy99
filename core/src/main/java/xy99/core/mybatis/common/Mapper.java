package xy99.core.mybatis.common;

/**
 * Created by hand on 2018/1/1.
 */
import com.hand.hap.mybatis.common.BaseMapper;
import com.hand.hap.mybatis.common.ExampleMapper;
import com.hand.hap.mybatis.common.Marker;
import com.hand.hap.mybatis.common.RowBoundsMapper;

public interface Mapper<T> extends BaseMapper<T>, ExampleMapper<T>, RowBoundsMapper<T>, Marker {
}
