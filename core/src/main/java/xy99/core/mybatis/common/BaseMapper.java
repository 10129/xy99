package xy99.core.mybatis.common;


import xy99.core.mybatis.common.base.BaseDeleteMapper;
import xy99.core.mybatis.common.base.BaseInsertMapper;
import xy99.core.mybatis.common.base.BaseSelectMapper;
import xy99.core.mybatis.common.base.BaseUpdateMapper;

public interface BaseMapper<T> extends BaseSelectMapper<T>, BaseInsertMapper<T>, BaseUpdateMapper<T>, BaseDeleteMapper<T> {
}
