package xy99.core.mybatis.provider;


import com.hand.hap.mybatis.mapperhelper.MapperHelper;
import com.hand.hap.mybatis.mapperhelper.MapperTemplate;
import com.hand.hap.mybatis.mapperhelper.SqlHelper;
import org.apache.ibatis.mapping.MappedStatement;

public class ExampleProvider extends MapperTemplate {
    public ExampleProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectCountByExample(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        return sql.toString();
    }

    public String deleteByExample(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        return sql.toString();
    }

    public String selectByExample(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("<if test=\"distinct\">distinct</if>");
        sql.append(SqlHelper.exampleSelectColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.exampleWhereClause());
        sql.append(SqlHelper.exampleOrderBy(entityClass));
        return sql.toString();
    }

    public String selectByExampleAndRowBounds(MappedStatement ms) {
        return this.selectByExample(ms);
    }

    public String updateByExampleSelective(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass), "example"));
        sql.append(SqlHelper.updateSetColumns(entityClass, "record", true, this.isNotEmpty()));
        sql.append(SqlHelper.updateByExampleWhereClause());
        return sql.toString();
    }

    public String updateByExample(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass), "example"));
        sql.append(SqlHelper.updateSetColumns(entityClass, "record", false, false));
        sql.append(SqlHelper.updateByExampleWhereClause());
        return sql.toString();
    }
}
