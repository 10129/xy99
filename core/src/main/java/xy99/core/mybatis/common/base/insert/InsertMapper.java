package xy99.core.mybatis.common.base.insert;

/**
 * Created by hand on 2018/1/1.
 */
import com.hand.hap.mybatis.provider.base.BaseInsertProvider;
import org.apache.ibatis.annotations.InsertProvider;

public interface InsertMapper<T> {
    @InsertProvider(
            type = BaseInsertProvider.class,
            method = "dynamicSQL"
    )
    int insert(T var1);
}