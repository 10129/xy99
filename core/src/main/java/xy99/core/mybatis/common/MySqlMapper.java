package xy99.core.mybatis.common;

import com.hand.hap.mybatis.common.special.InsertListMapper;
import com.hand.hap.mybatis.common.special.InsertUseGeneratedKeysMapper;

public interface MySqlMapper<T> extends InsertListMapper<T>, InsertUseGeneratedKeysMapper<T> {
}
