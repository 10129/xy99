package xy99.core.mybatis.common.base;

import com.hand.hap.mybatis.common.base.select.SelectAllMapper;
import com.hand.hap.mybatis.common.base.select.SelectByPrimaryKeyMapper;
import com.hand.hap.mybatis.common.base.select.SelectCountMapper;
import com.hand.hap.mybatis.common.base.select.SelectMapper;
import com.hand.hap.mybatis.common.base.select.SelectOneMapper;
import com.hand.hap.mybatis.common.base.select.SelectOptionsMapper;

public interface BaseSelectMapper<T> extends SelectOneMapper<T>, SelectMapper<T>, SelectAllMapper<T>, SelectCountMapper<T>, SelectByPrimaryKeyMapper<T>, SelectOptionsMapper<T> {
}

