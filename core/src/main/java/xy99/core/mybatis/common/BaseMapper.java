package xy99.core.mybatis.common;


import com.hand.hap.mybatis.common.base.BaseDeleteMapper;
import com.hand.hap.mybatis.common.base.BaseInsertMapper;
import com.hand.hap.mybatis.common.base.BaseSelectMapper;
import com.hand.hap.mybatis.common.base.BaseUpdateMapper;

public interface BaseMapper<T> extends BaseSelectMapper<T>, BaseInsertMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T> {
}
