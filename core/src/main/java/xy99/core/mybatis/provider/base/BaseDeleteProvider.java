package xy99.core.mybatis.provider.base;


import xy99.core.mybatis.mapperhelper.MapperHelper;
import xy99.core.mybatis.mapperhelper.MapperTemplate;
import xy99.core.mybatis.mapperhelper.SqlHelper;
import xy99.core.mybatis.provider.base.BaseUpdateProvider;
import org.apache.ibatis.mapping.MappedStatement;

public class BaseDeleteProvider extends MapperTemplate {
    public BaseDeleteProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String delete(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty()));
        return sql.toString();
    }

    public String deleteByPrimaryKey(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        BaseUpdateProvider.appendObjectVersionNumber(sql, entityClass);
        return sql.toString();
    }
}
