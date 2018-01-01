package xy99.core.mybatis.common.base.update;
import xy99.core.mybatis.common.Criteria;
import xy99.core.mybatis.provider.base.BaseUpdateProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateByPrimaryKeyOptionsMapper<T> {
    @UpdateProvider(
            type = BaseUpdateProvider.class,
            method = "dynamicSQL"
    )
    int updateByPrimaryKeyOptions(@Param("dto") T var1, @Param("criteria") Criteria var2);
}

