package xy99.core.mybatis.common.base.select;

import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.provider.base.BaseSelectProvider;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectOptionsMapper<T> {
    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "selectOptionsByPrimaryKey"
    )
    T selectOptionsByPrimaryKey(T var1);

    @SelectProvider(
            type = BaseSelectProvider.class,
            method = "selectOptions"
    )
    List<T> selectOptions(@Param("dto") T var1, @Param("criteria") Criteria var2);
}
