package xy99.core.mybatis.common;

import com.hand.hap.mybatis.common.example.DeleteByExampleMapper;
import com.hand.hap.mybatis.common.example.SelectByExampleMapper;
import com.hand.hap.mybatis.common.example.SelectCountByExampleMapper;
import com.hand.hap.mybatis.common.example.UpdateByExampleMapper;
import com.hand.hap.mybatis.common.example.UpdateByExampleSelectiveMapper;

public interface ExampleMapper<T> extends SelectByExampleMapper<T>, SelectCountByExampleMapper<T>, DeleteByExampleMapper<T>, UpdateByExampleMapper<T>, UpdateByExampleSelectiveMapper<T> {
}
