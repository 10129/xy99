package xy99.core.mybatis.common.rowbounds;

/**
 * Created by hand on 2018/1/1.
 */
import xy99.core.mybatis.provider.base.BaseSelectProvider;
import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

public interface SelectRowBoundsMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByRowBounds(T var1, RowBounds var2);
}
