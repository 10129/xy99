package xy99.core.mybatis.common;

import xy99.core.mybatis.common.special.InsertListMapper;
import xy99.core.mybatis.common.special.InsertUseGeneratedKeysMapper;

public interface MySqlMapper<T> extends InsertListMapper<T>, InsertUseGeneratedKeysMapper<T> {
}
