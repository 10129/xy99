package xy99.core.mybatis.common.base.update;
import com.hand.hap.mybatis.common.Criteria;
import com.hand.hap.mybatis.provider.base.BaseUpdateProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateByPrimaryKeyOptionsMapper<T> {
    @UpdateProvider(
            type = BaseUpdateProvider.class,
            method = "dynamicSQL"
    )
    int updateByPrimaryKeyOptions(@Param("dto") T var1, @Param("criteria") Criteria var2);
}

