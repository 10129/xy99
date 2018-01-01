package xy99.core.mybatis.common.base.delete;

import com.hand.hap.mybatis.provider.base.BaseDeleteProvider;
import org.apache.ibatis.annotations.DeleteProvider;

public interface DeleteMapper<T> {
    @DeleteProvider(
            type = BaseDeleteProvider.class,
            method = "dynamicSQL"
    )
    int delete(T var1);
}
