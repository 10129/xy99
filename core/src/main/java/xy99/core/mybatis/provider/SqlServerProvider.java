package xy99.core.mybatis.provider;


import com.hand.hap.mybatis.mapperhelper.MapperHelper;
import com.hand.hap.mybatis.mapperhelper.MapperTemplate;
import com.hand.hap.mybatis.mapperhelper.SqlHelper;
import org.apache.ibatis.mapping.MappedStatement;

public class SqlServerProvider extends MapperTemplate {
    public SqlServerProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String insert(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
        sql.append(SqlHelper.insertValuesColumns(entityClass, true, false, false));
        return sql.toString();
    }

    public String insertSelective(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true, true, this.isNotEmpty()));
        sql.append(SqlHelper.insertValuesColumns(entityClass, true, true, this.isNotEmpty()));
        return sql.toString();
    }
}
