package xy99.core.mybatis.common.base;

import com.hand.hap.mybatis.common.base.delete.DeleteByPrimaryKeyMapper;
import com.hand.hap.mybatis.common.base.delete.DeleteMapper;

public interface BaseDeleteMapper<T> extends DeleteMapper<T>, DeleteByPrimaryKeyMapper<T> {
}
