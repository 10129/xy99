package xy99.core.mybatis.common.example;

import xy99.core.mybatis.provider.ExampleProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateByExampleMapper<T> {
    @UpdateProvider(
            type = ExampleProvider.class,
            method = "dynamicSQL"
    )
    int updateByExample(@Param("record") T var1, @Param("example") Object var2);
}
