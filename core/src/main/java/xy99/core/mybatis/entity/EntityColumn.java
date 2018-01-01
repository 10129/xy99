package xy99.core.mybatis.entity;


import xy99.core.mybatis.annotation.Condition;
import xy99.core.mybatis.common.query.JoinColumn;
import xy99.core.mybatis.common.query.JoinTable;
import xy99.core.mybatis.common.query.Where;
import xy99.core.mybatis.entity.EntityTable;
import xy99.core.mybatis.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class EntityColumn {
    private EntityTable table;
    private String property;
    private String column;
    private Class<?> javaType;
    private JdbcType jdbcType;
    private Class<? extends TypeHandler<?>> typeHandler;
    private String sequenceName;
    private boolean id = false;
    private boolean uuid = false;
    private boolean identity = false;
    private String generator;
    private String orderBy;
    private boolean selectable = true;
    private boolean insertable = true;
    private boolean updatable = true;
    private boolean isMultiLanguageField = false;
    private Condition condition;
    private List<JoinTable> joinTables;
    private JoinColumn joinColumn;
    private Where where;

    public EntityColumn() {
    }

    public EntityColumn(EntityTable table) {
        this.table = table;
    }

    public EntityTable getTable() {
        return this.table;
    }

    public void setTable(EntityTable table) {
        this.table = table;
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return this.column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Class<?> getJavaType() {
        return this.javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return this.jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public Class<? extends TypeHandler<?>> getTypeHandler() {
        return this.typeHandler;
    }

    public void setTypeHandler(Class<? extends TypeHandler<?>> typeHandler) {
        this.typeHandler = typeHandler;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public boolean isId() {
        return this.id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isUuid() {
        return this.uuid;
    }

    public void setUuid(boolean uuid) {
        this.uuid = uuid;
    }

    public boolean isIdentity() {
        return this.identity;
    }

    public void setIdentity(boolean identity) {
        this.identity = identity;
    }

    public String getGenerator() {
        return this.generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isInsertable() {
        return this.insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isUpdatable() {
        return this.updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public String getColumnEqualsHolder() {
        return this.getColumnEqualsHolder((String)null);
    }

    public String getColumnEqualsHolder(String entityName) {
        return this.column + " = " + this.getColumnHolder(entityName);
    }

    public String getColumnLikeHolder() {
        return this.getColumnLikeHolder((String)null);
    }

    public String getColumnLikeHolder(String entityName) {
        return this.column + " LIKE concat(\'%\',concat(" + this.getColumnHolder(entityName) + ",\'%\'))";
    }

    public String getColumnHolderWithOperator(String operator, boolean autoWrap) {
        if("LIKE".equalsIgnoreCase(operator)) {
            return autoWrap?this.column + " LIKE concat(\'%\',concat(" + this.getColumnHolder((String)null) + ",\'%\'))":this.column + " LIKE " + this.getColumnHolder((String)null);
        } else {
            String newOperator = operator.replace(">", "&gt;").replace("<", "&lt;");
            return this.column + newOperator + this.getColumnHolder((String)null);
        }
    }

    public String getColumnHolder() {
        return this.getColumnHolder((String)null);
    }

    public String getColumnHolder(String entityName) {
        return this.getColumnHolder(entityName, (String)null);
    }

    public String getColumnHolder(String entityName, String suffix) {
        return this.getColumnHolder(entityName, suffix, (String)null);
    }

    public String getColumnHolderWithComma(String entityName, String suffix) {
        return this.getColumnHolder(entityName, suffix, ",");
    }

    public String getColumnHolder(String entityName, String suffix, String separator) {
        StringBuilder sb = new StringBuilder("#{");
        if(StringUtil.isNotEmpty(entityName)) {
            sb.append(entityName);
            sb.append(".");
        }

        sb.append(this.property);
        if(StringUtil.isNotEmpty(suffix)) {
            sb.append(suffix);
        }

        if(this.jdbcType != null) {
            sb.append(",jdbcType=");
            sb.append(this.jdbcType.toString());
        } else if(this.typeHandler != null) {
            sb.append(",typeHandler=");
            sb.append(this.typeHandler.getCanonicalName());
        } else if(!this.javaType.isArray() && !Number.class.isAssignableFrom(this.javaType)) {
            sb.append(",javaType=");
            sb.append(this.javaType.getCanonicalName());
        }

        sb.append("}");
        if(StringUtil.isNotEmpty(separator)) {
            sb.append(separator);
        }

        return sb.toString();
    }

    public boolean isMultiLanguageField() {
        return this.isMultiLanguageField;
    }

    public void setMultiLanguageField(boolean multiLanguageField) {
        this.isMultiLanguageField = multiLanguageField;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public List<JoinTable> getJoinTables() {
        return this.joinTables;
    }

    public JoinTable findJoinTableByName(String joinName) {
        JoinTable joinTable = null;
        if(this.joinTables != null && joinName != null) {
            Iterator var3 = this.joinTables.iterator();

            while(var3.hasNext()) {
                JoinTable jt = (JoinTable)var3.next();
                if(joinName.equalsIgnoreCase(jt.name())) {
                    joinTable = jt;
                    break;
                }
            }
        }

        return joinTable;
    }

    public void addJoinTable(JoinTable joinTable) {
        if(this.joinTables == null) {
            this.joinTables = new ArrayList();
        }

        this.joinTables.add(joinTable);
    }

    public JoinColumn getJoinColumn() {
        return this.joinColumn;
    }

    public void setJoinColumn(JoinColumn joinColumn) {
        this.joinColumn = joinColumn;
    }

    public Where getWhere() {
        return this.where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            EntityColumn that = (EntityColumn)o;
            if(this.id != that.id) {
                return false;
            } else if(this.uuid != that.uuid) {
                return false;
            } else if(this.identity != that.identity) {
                return false;
            } else {
                if(this.table != null) {
                    if(!this.table.equals(that.table)) {
                        return false;
                    }
                } else if(that.table != null) {
                    return false;
                }

                if(this.property != null) {
                    if(!this.property.equals(that.property)) {
                        return false;
                    }
                } else if(that.property != null) {
                    return false;
                }

                label105: {
                    if(this.column != null) {
                        if(this.column.equals(that.column)) {
                            break label105;
                        }
                    } else if(that.column == null) {
                        break label105;
                    }

                    return false;
                }

                label98: {
                    if(this.javaType != null) {
                        if(this.javaType.equals(that.javaType)) {
                            break label98;
                        }
                    } else if(that.javaType == null) {
                        break label98;
                    }

                    return false;
                }

                if(this.jdbcType != that.jdbcType) {
                    return false;
                } else {
                    label90: {
                        if(this.typeHandler != null) {
                            if(this.typeHandler.equals(that.typeHandler)) {
                                break label90;
                            }
                        } else if(that.typeHandler == null) {
                            break label90;
                        }

                        return false;
                    }

                    if(this.sequenceName != null) {
                        if(!this.sequenceName.equals(that.sequenceName)) {
                            return false;
                        }
                    } else if(that.sequenceName != null) {
                        return false;
                    }

                    if(this.generator != null) {
                        if(!this.generator.equals(that.generator)) {
                            return false;
                        }
                    } else if(that.generator != null) {
                        return false;
                    }

                    boolean var10000;
                    label159: {
                        if(this.orderBy != null) {
                            if(this.orderBy.equals(that.orderBy)) {
                                break label159;
                            }
                        } else if(that.orderBy == null) {
                            break label159;
                        }

                        var10000 = false;
                        return var10000;
                    }

                    var10000 = true;
                    return var10000;
                }
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.table != null?this.table.hashCode():0;
        result = 31 * result + (this.property != null?this.property.hashCode():0);
        result = 31 * result + (this.column != null?this.column.hashCode():0);
        result = 31 * result + (this.javaType != null?this.javaType.hashCode():0);
        result = 31 * result + (this.jdbcType != null?this.jdbcType.hashCode():0);
        result = 31 * result + (this.typeHandler != null?this.typeHandler.hashCode():0);
        result = 31 * result + (this.sequenceName != null?this.sequenceName.hashCode():0);
        result = 31 * result + (this.id?1:0);
        result = 31 * result + (this.uuid?1:0);
        result = 31 * result + (this.identity?1:0);
        result = 31 * result + (this.generator != null?this.generator.hashCode():0);
        result = 31 * result + (this.orderBy != null?this.orderBy.hashCode():0);
        return result;
    }
}
