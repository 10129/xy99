package xy99.core.mybatis.provider.base;


import xy99.core.mybatis.common.Criteria;
import xy99.core.mybatis.mapperhelper.EntityHelper;
import xy99.core.mybatis.mapperhelper.MapperHelper;
import xy99.core.mybatis.mapperhelper.MapperTemplate;
import xy99.core.mybatis.mapperhelper.SqlHelper;
import xy99.core.system.dto.BaseDTO;
import java.util.Map;
import org.apache.ibatis.mapping.MappedStatement;

public class BaseSelectProvider extends MapperTemplate {
    public BaseSelectProvider() {
    }

    public BaseSelectProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectOne(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if(isMl) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns_TL(entityClass, this.isNotEmpty(), true));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty()));
        }

        return sql.toString();
    }

    public String select(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if(isMl) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns_TL(entityClass, this.isNotEmpty(), false));
            sql.append(SqlHelper.orderByDefault_TL(entityClass));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty()));
            sql.append(SqlHelper.orderByDefault(entityClass));
        }

        return sql.toString();
    }

    public String selectByRowBounds(MappedStatement ms) {
        return this.select(ms);
    }

    public String selectByPrimaryKey(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if(isMl) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.wherePKColumns_TL(entityClass));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.wherePKColumns(entityClass));
        }

        return sql.toString();
    }

    public String selectCount(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectCount(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty()));
        return sql.toString();
    }

    public String selectAll(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        boolean isMl = EntityHelper.getEntityTable(entityClass).isSupportMultiLanguage();
        return this.selectAllResult(ms, Boolean.valueOf(isMl));
    }

    private String selectAllResult(MappedStatement ms, Boolean isMultiLanguage) {
        Class entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        if(isMultiLanguage.booleanValue()) {
            sql.append(SqlHelper.selectAllColumns_TL(entityClass));
            sql.append(SqlHelper.fromTable_TL(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.orderByDefault_TL(entityClass));
        } else {
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
            sql.append(SqlHelper.orderByDefault(entityClass));
        }

        return sql.toString();
    }

    public String selectAllWithoutMultiLanguage(MappedStatement ms) {
        return this.selectAllResult(ms, Boolean.valueOf(false));
    }

    private void initResultType(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
    }

    public void selectOptions(MappedStatement ms) {
        this.initResultType(ms);
    }

    public void selectOptionsByPrimaryKey(MappedStatement ms) {
        this.initResultType(ms);
    }

    public String selectOptionsByPrimaryKey(BaseDTO dto) {
        return SqlHelper.buildSelectByPrimaryKeySQL(dto);
    }

    public String selectOptions(Map<String, Object> parameter) {
        BaseDTO dto = (BaseDTO)parameter.get("dto");
        Criteria criteria = (Criteria)parameter.get("criteria");
        return SqlHelper.buildSelectSelectiveSql(dto, criteria);
    }
}
