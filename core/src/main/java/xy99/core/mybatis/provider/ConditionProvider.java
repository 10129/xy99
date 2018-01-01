package xy99.core.mybatis.provider;


import xy99.core.mybatis.mapperhelper.MapperHelper;
import xy99.core.mybatis.mapperhelper.MapperTemplate;
import xy99.core.mybatis.provider.ExampleProvider;
import org.apache.ibatis.mapping.MappedStatement;

public class ConditionProvider extends MapperTemplate {
    private ExampleProvider exampleProvider;

    public ConditionProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
        this.exampleProvider = new ExampleProvider(mapperClass, mapperHelper);
    }

    public String selectCountByCondition(MappedStatement ms) {
        return this.exampleProvider.selectCountByExample(ms);
    }

    public String deleteByCondition(MappedStatement ms) {
        return this.exampleProvider.deleteByExample(ms);
    }

    public String selectByCondition(MappedStatement ms) {
        return this.exampleProvider.selectByExample(ms);
    }

    public String selectByConditionAndRowBounds(MappedStatement ms) {
        return this.exampleProvider.selectByExample(ms);
    }

    public String updateByConditionSelective(MappedStatement ms) {
        return this.exampleProvider.updateByExampleSelective(ms);
    }

    public String updateByCondition(MappedStatement ms) {
        return this.exampleProvider.updateByExample(ms);
    }
}
