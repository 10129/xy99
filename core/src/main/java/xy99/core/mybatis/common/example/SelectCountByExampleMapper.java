package xy99.core.mybatis.common.example;

import xy99.core.mybatis.provider.ExampleProvider;
import org.apache.ibatis.annotations.SelectProvider;

public interface SelectCountByExampleMapper<T> {
    @SelectProvider(
            type = ExampleProvider.class,
            method = "dynamicSQL"
    )
    int selectCountByExample(Object var1);
}
