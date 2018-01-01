package xy99.core.mybatis.common.example;

import xy99.core.mybatis.provider.ExampleProvider;
import java.util.List;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectByExampleMapper<T> {
    @SelectProvider(
            type = ExampleProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByExample(Object var1);
}