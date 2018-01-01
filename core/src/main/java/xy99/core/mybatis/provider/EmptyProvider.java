package xy99.core.mybatis.provider;

import xy99.core.mybatis.mapperhelper.MapperHelper;
import xy99.core.mybatis.mapperhelper.MapperTemplate;

public class EmptyProvider extends MapperTemplate {
    public EmptyProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public boolean supportMethod(String msId) {
        return false;
    }
}
