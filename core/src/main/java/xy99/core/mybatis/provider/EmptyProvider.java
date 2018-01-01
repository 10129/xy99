package xy99.core.mybatis.provider;

import com.hand.hap.mybatis.mapperhelper.MapperHelper;
import com.hand.hap.mybatis.mapperhelper.MapperTemplate;

public class EmptyProvider extends MapperTemplate {
    public EmptyProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public boolean supportMethod(String msId) {
        return false;
    }
}
