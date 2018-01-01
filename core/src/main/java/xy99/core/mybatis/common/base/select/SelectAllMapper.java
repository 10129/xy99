package xy99.core.mybatis.common.base.select;

import com.hand.hap.mybatis.provider.base.BaseSelectProvider;
import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectAllMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectAll();

    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectAllWithoutMultiLanguage();
}
