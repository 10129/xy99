package xy99.core.mybatis.common.base.update;

import com.hand.hap.mybatis.provider.base.BaseUpdateProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface UpdateByPrimaryKeySelectiveMapper<T> {
    @UpdateProvider(
            type = BaseUpdateProvider.class,
            method = "dynamicSQL"
    )
    int updateByPrimaryKeySelective(T var1);
}
