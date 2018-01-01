package xy99.core.mybatis.common.rowbounds;

/**
 * Created by hand on 2018/1/1.
 */
import xy99.core.mybatis.provider.ConditionProvider;
import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

public interface SelectByConditionRowBoundsMapper<T> {
    @SelectProvider(
            type = ConditionProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByConditionAndRowBounds(Object var1, RowBounds var2);
}
