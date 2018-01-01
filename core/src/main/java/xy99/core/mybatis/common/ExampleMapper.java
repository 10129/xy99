package xy99.core.mybatis.common;

import xy99.core.mybatis.common.example.DeleteByExampleMapper;
import xy99.core.mybatis.common.example.SelectByExampleMapper;
import xy99.core.mybatis.common.example.SelectCountByExampleMapper;
import xy99.core.mybatis.common.example.UpdateByExampleMapper;
import xy99.core.mybatis.common.example.UpdateByExampleSelectiveMapper;

public interface ExampleMapper<T> extends SelectByExampleMapper<T>, SelectCountByExampleMapper<T>, DeleteByExampleMapper<T>, UpdateByExampleMapper<T>, UpdateByExampleSelectiveMapper<T> {
}
