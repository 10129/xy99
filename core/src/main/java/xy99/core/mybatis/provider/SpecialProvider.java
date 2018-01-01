package xy99.core.mybatis.provider;


import xy99.core.mybatis.entity.EntityColumn;
import xy99.core.mybatis.mapperhelper.EntityHelper;
import xy99.core.mybatis.mapperhelper.MapperHelper;
import xy99.core.mybatis.mapperhelper.MapperTemplate;
import xy99.core.mybatis.mapperhelper.SqlHelper;
import java.util.Iterator;
import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;

public class SpecialProvider extends MapperTemplate {
    public SpecialProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String insertList(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        Set columnList = EntityHelper.getColumns(entityClass);
        Iterator var5 = columnList.iterator();

        while(var5.hasNext()) {
            EntityColumn column = (EntityColumn)var5.next();
            if(!column.isId() && column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }

        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }

    public String insertUseGeneratedKeys(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
        sql.append(SqlHelper.insertValuesColumns(entityClass, true, false, false));
        return sql.toString();
    }
}
