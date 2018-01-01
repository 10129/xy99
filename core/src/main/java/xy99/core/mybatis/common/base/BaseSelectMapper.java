package xy99.core.mybatis.common.base;

import xy99.core.mybatis.common.base.select.SelectAllMapper;
import xy99.core.mybatis.common.base.select.SelectByPrimaryKeyMapper;
import xy99.core.mybatis.common.base.select.SelectCountMapper;
import xy99.core.mybatis.common.base.select.SelectMapper;
import xy99.core.mybatis.common.base.select.SelectOneMapper;
import xy99.core.mybatis.common.base.select.SelectOptionsMapper;

public interface BaseSelectMapper<T> extends SelectOneMapper<T>, SelectMapper<T>, SelectAllMapper<T>, SelectCountMapper<T>, SelectByPrimaryKeyMapper<T>, SelectOptionsMapper<T> {
}

