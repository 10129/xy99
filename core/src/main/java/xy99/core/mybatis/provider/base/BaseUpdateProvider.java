package xy99.core.mybatis.provider.base;


import com.hand.hap.mybatis.mapperhelper.MapperHelper;
import com.hand.hap.mybatis.mapperhelper.MapperTemplate;
import com.hand.hap.mybatis.mapperhelper.SqlHelper;
import com.hand.hap.system.dto.BaseDTO;
import org.apache.ibatis.mapping.MappedStatement;

public class BaseUpdateProvider extends MapperTemplate {
    public BaseUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateByPrimaryKey(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, (String)null, false, false));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        appendObjectVersionNumber(sql, entityClass);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumns(entityClass, (String)null, true, this.isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        appendObjectVersionNumber(sql, entityClass);
        return sql.toString();
    }

    public String updateByPrimaryKeyOptions(MappedStatement ms) {
        Class entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.updateSetColumnsWithOption(entityClass));
        sql.append(SqlHelper.wherePKColumns(entityClass, "dto"));
        appendObjectVersionNumberWithOptions(sql, entityClass);
        return sql.toString();
    }

    public static void appendObjectVersionNumber(StringBuilder sb, Class<?> entityClass) {
        if(BaseDTO.class.isAssignableFrom(entityClass)) {
            sb.append("<if test=\"objectVersionNumber!=null\">");
            sb.append(" AND OBJECT_VERSION_NUMBER=#{objectVersionNumber,jdbcType=DECIMAL}");
            sb.append("</if>");
        }
    }

    public static void appendObjectVersionNumberWithOptions(StringBuilder sb, Class<?> entityClass) {
        if(BaseDTO.class.isAssignableFrom(entityClass)) {
            sb.append("<if test=\"dto.objectVersionNumber!=null\">");
            sb.append(" AND OBJECT_VERSION_NUMBER=#{dto.objectVersionNumber,jdbcType=DECIMAL}");
            sb.append("</if>");
        }
    }
}
