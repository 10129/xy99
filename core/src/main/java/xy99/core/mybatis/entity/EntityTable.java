package xy99.core.mybatis.entity;


import xy99.core.mybatis.entity.EntityColumn;
import xy99.core.mybatis.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Table;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping.Builder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;

public class EntityTable {
    private static final char ALIAS_START = 'A';
    private int currentAliasCharIndex = 0;
    private String name;
    private String catalog;
    private String schema;
    private String orderByClause;
    private String baseSelect;
    private Set<EntityColumn> allColumns = new LinkedHashSet();
    private Set<EntityColumn> entityClassColumns;
    private Set<EntityColumn> entityClassPKColumns;
    private List<String> keyProperties;
    private List<String> keyColumns;
    private ResultMap resultMap;
    private Class<?> entityClass;
    private Set<EntityColumn> sortColumns = new LinkedHashSet();
    private Map<String, String> aliasMapping = new HashMap();
    private Map<String, EntityColumn> joinMapping = new HashMap();
    private List<EntityColumn> whereColumns = new ArrayList();
    private boolean supportMultiLanguage = false;

    public EntityTable(Class<?> entityClass) {
        this.entityClass = entityClass;
        this.createAlias(entityClass.getCanonicalName());
    }

    public Class<?> getEntityClass() {
        return this.entityClass;
    }

    public void setTable(Table table) {
        this.name = table.name();
        this.catalog = table.catalog();
        this.schema = table.schema();
    }

    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    public void setKeyProperties(List<String> keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return this.schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getBaseSelect() {
        return this.baseSelect;
    }

    public void setBaseSelect(String baseSelect) {
        this.baseSelect = baseSelect;
    }

    public String getPrefix() {
        return StringUtil.isNotEmpty(this.catalog)?this.catalog:(StringUtil.isNotEmpty(this.schema)?this.schema:"");
    }

    public Set<EntityColumn> getEntityClassColumns() {
        return this.entityClassColumns;
    }

    public void setEntityClassColumns(Set<EntityColumn> entityClassColumns) {
        this.entityClassColumns = entityClassColumns;
    }

    public Set<EntityColumn> getEntityClassPKColumns() {
        return this.entityClassPKColumns;
    }

    public void setEntityClassPKColumns(Set<EntityColumn> entityClassPKColumns) {
        this.entityClassPKColumns = entityClassPKColumns;
    }

    public String[] getKeyProperties() {
        return this.keyProperties != null && !this.keyProperties.isEmpty()?(String[])this.keyProperties.toArray(new String[0]):new String[0];
    }

    public void setKeyProperties(String keyProperty) {
        if(this.keyProperties == null) {
            this.keyProperties = new ArrayList();
            this.keyProperties.add(keyProperty);
        } else {
            this.keyProperties.add(keyProperty);
        }

    }

    public String[] getKeyColumns() {
        return this.keyColumns != null && !this.keyColumns.isEmpty()?(String[])this.keyColumns.toArray(new String[0]):new String[0];
    }

    public void setKeyColumns(String keyColumn) {
        if(this.keyColumns == null) {
            this.keyColumns = new ArrayList();
            this.keyColumns.add(keyColumn);
        } else {
            this.keyColumns.add(keyColumn);
        }

    }

    public boolean isSupportMultiLanguage() {
        return this.supportMultiLanguage;
    }

    public void setSupportMultiLanguage(boolean supportMultiLanguage) {
        this.supportMultiLanguage = supportMultiLanguage;
    }

    public ResultMap getResultMap(Configuration configuration) {
        if(this.resultMap != null) {
            return this.resultMap;
        } else if(this.allColumns != null && !this.allColumns.isEmpty()) {
            ArrayList resultMappings = new ArrayList();
            Iterator builder = this.allColumns.iterator();

            while(builder.hasNext()) {
                EntityColumn entityColumn = (EntityColumn)builder.next();
                Builder builder1 = new Builder(configuration, entityColumn.getProperty(), entityColumn.getColumn(), entityColumn.getJavaType());
                if(entityColumn.getJdbcType() != null) {
                    builder1.jdbcType(entityColumn.getJdbcType());
                }

                if(entityColumn.getTypeHandler() != null) {
                    try {
                        builder1.typeHandler((TypeHandler)entityColumn.getTypeHandler().newInstance());
                    } catch (Exception var7) {
                        throw new RuntimeException(var7);
                    }
                }

                ArrayList flags = new ArrayList();
                if(entityColumn.isId()) {
                    flags.add(ResultFlag.ID);
                }

                builder1.flags(flags);
                resultMappings.add(builder1.build());
            }

            org.apache.ibatis.mapping.ResultMap.Builder builder2 = new org.apache.ibatis.mapping.ResultMap.Builder(configuration, "BaseMapperResultMap", this.entityClass, resultMappings, Boolean.valueOf(true));
            this.resultMap = builder2.build();
            return this.resultMap;
        } else {
            return null;
        }
    }

    public void createAlias(String key) {
        if(!this.aliasMapping.containsKey(key)) {
            this.aliasMapping.put(key, this.generateAlias());
        }

    }

    public String getAlias(String key) {
        return key == null?(String)this.aliasMapping.get(this.entityClass.getCanonicalName()):(String)this.aliasMapping.get(key);
    }

    public Map<String, EntityColumn> getJoinMapping() {
        return this.joinMapping;
    }

    public String getAlias() {
        return this.getAlias((String)null);
    }

    private String generateAlias() {
        return String.valueOf((char)(65 + this.currentAliasCharIndex++));
    }

    public List<EntityColumn> getWhereColumns() {
        return this.whereColumns;
    }

    public Set<EntityColumn> getSortColumns() {
        return this.sortColumns;
    }

    public Set<EntityColumn> getAllColumns() {
        return this.allColumns;
    }

    public EntityColumn findColumnByProperty(String property) {
        EntityColumn entityColumn = null;
        Iterator var3 = this.getAllColumns().iterator();

        while(var3.hasNext()) {
            EntityColumn column = (EntityColumn)var3.next();
            if(column.getProperty().equals(property)) {
                entityColumn = column;
                break;
            }
        }

        return entityColumn;
    }
}
