package xy99.core.mybatis.common.base.insert;

/**
 * Created by hand on 2018/1/1.
 */

import xy99.core.mybatis.provider.base.BaseInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;

public interface InsertSelectiveMapper<T> {
    @InsertProvider(
            type = BaseInsertProvider.class,
            method = "dynamicSQL"
    )
    int insertSelective(T var1);
}
