package xy99.core.mybatis.common.example;

import xy99.core.mybatis.provider.ExampleProvider;
import org.apache.ibatis.annotations.DeleteProvider;

public interface DeleteByExampleMapper<T> {
    @DeleteProvider(
            type = ExampleProvider.class,
            method = "dynamicSQL"
    )
    int deleteByExample(Object var1);
}
