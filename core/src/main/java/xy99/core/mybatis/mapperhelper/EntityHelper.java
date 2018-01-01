package xy99.core.mybatis.mapperhelper;


import com.hand.hap.core.annotation.MultiLanguage;
import com.hand.hap.core.annotation.MultiLanguageField;
import com.hand.hap.mybatis.annotation.ColumnType;
import com.hand.hap.mybatis.annotation.Condition;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.mybatis.annotation.NameStyle;
import com.hand.hap.mybatis.code.IdentityDialect;
import com.hand.hap.mybatis.code.Style;
import com.hand.hap.mybatis.common.query.Comparison;
import com.hand.hap.mybatis.common.query.JoinColumn;
import com.hand.hap.mybatis.common.query.JoinOn;
import com.hand.hap.mybatis.common.query.JoinTable;
import com.hand.hap.mybatis.common.query.Where;
import com.hand.hap.mybatis.entity.Config;
import com.hand.hap.mybatis.entity.EntityColumn;
import com.hand.hap.mybatis.entity.EntityField;
import com.hand.hap.mybatis.entity.EntityTable;
import com.hand.hap.mybatis.mapperhelper.FieldHelper;
import com.hand.hap.mybatis.util.StringUtil;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.JoinType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.UnknownTypeHandler;

public class EntityHelper {
    private static final Map<Class<?>, EntityTable> entityTableMap = new HashMap();

    private EntityHelper() {
    }

    public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = (EntityTable)entityTableMap.get(entityClass);
        if(entityTable == null) {
            throw new RuntimeException("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        } else {
            return entityTable;
        }
    }

    public static EntityTable getEntityTable(String tableName) {
        EntityTable entityTable = null;
        Iterator var2 = entityTableMap.values().iterator();

        while(var2.hasNext()) {
            EntityTable entity = (EntityTable)var2.next();
            if(entity.getName().equalsIgnoreCase(tableName)) {
                entityTable = entity;
                break;
            }
        }

        if(null == entityTable) {
            throw new RuntimeException("无法通过表名" + tableName + "获取对应的表实体类!");
        } else {
            return entityTable;
        }
    }

    public static String buildJoinKey(JoinTable jt) {
        return jt.target().getCanonicalName() + "." + jt.name();
    }

    public static String getOrderByClause(Class<?> entityClass) {
        EntityTable table = getEntityTable(entityClass);
        if(table.getOrderByClause() != null) {
            return table.getOrderByClause();
        } else {
            StringBuilder orderBy = new StringBuilder();
            Iterator var3 = table.getEntityClassColumns().iterator();

            while(var3.hasNext()) {
                EntityColumn column = (EntityColumn)var3.next();
                if(column.getOrderBy() != null) {
                    if(orderBy.length() != 0) {
                        orderBy.append(",");
                    }

                    orderBy.append(column.getColumn()).append(" ").append(column.getOrderBy());
                }
            }

            table.setOrderByClause(orderBy.toString());
            return table.getOrderByClause();
        }
    }

    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    public static Set<EntityColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }

    public static String getSelectColumns(Class<?> entityClass) {
        EntityTable entityTable = getEntityTable(entityClass);
        if(entityTable.getBaseSelect() != null) {
            return entityTable.getBaseSelect();
        } else {
            Set columnList = getColumns(entityClass);
            StringBuilder selectBuilder = new StringBuilder();
            boolean skipAlias = Map.class.isAssignableFrom(entityClass);
            Iterator var5 = columnList.iterator();

            while(true) {
                while(var5.hasNext()) {
                    EntityColumn entityColumn = (EntityColumn)var5.next();
                    selectBuilder.append(entityColumn.getColumn());
                    if(!skipAlias && !entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty())) {
                        if(entityColumn.getColumn().substring(1, entityColumn.getColumn().length() - 1).equalsIgnoreCase(entityColumn.getProperty())) {
                            selectBuilder.append(",");
                        } else {
                            selectBuilder.append(" AS ").append(entityColumn.getProperty()).append(",");
                        }
                    } else {
                        selectBuilder.append(",");
                    }
                }

                entityTable.setBaseSelect(selectBuilder.substring(0, selectBuilder.length() - 1));
                return entityTable.getBaseSelect();
            }
        }
    }

    /** @deprecated */
    @Deprecated
    public static String getAllColumns(Class<?> entityClass) {
        Set columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        Iterator var3 = columnList.iterator();

        while(var3.hasNext()) {
            EntityColumn entityColumn = (EntityColumn)var3.next();
            selectBuilder.append(entityColumn.getColumn()).append(",");
        }

        return selectBuilder.substring(0, selectBuilder.length() - 1);
    }

    /** @deprecated */
    @Deprecated
    public static String getPrimaryKeyWhere(Class<?> entityClass) {
        Set entityColumns = getPKColumns(entityClass);
        StringBuilder whereBuilder = new StringBuilder();
        Iterator var3 = entityColumns.iterator();

        while(var3.hasNext()) {
            EntityColumn column = (EntityColumn)var3.next();
            whereBuilder.append(column.getColumnEqualsHolder()).append(" AND ");
        }

        return whereBuilder.substring(0, whereBuilder.length() - 4);
    }

    public static synchronized void initEntityNameMap(Class<?> entityClass, Config config) {
        if(entityTableMap.get(entityClass) == null) {
            Style style = config.getStyle();
            if(entityClass.isAnnotationPresent(NameStyle.class)) {
                NameStyle entityTable = (NameStyle)entityClass.getAnnotation(NameStyle.class);
                style = entityTable.value();
            }

            EntityTable entityTable1 = null;
            if(entityClass.isAnnotationPresent(Table.class)) {
                Table fields = (Table)entityClass.getAnnotation(Table.class);
                if(!fields.name().equals("")) {
                    entityTable1 = new EntityTable(entityClass);
                    entityTable1.setTable(fields);
                }
            }

            if(entityTable1 == null) {
                entityTable1 = new EntityTable(entityClass);
                entityTable1.setName(StringUtil.convertByStyle(entityClass.getSimpleName(), style));
            }

            if(entityClass.isAnnotationPresent(MultiLanguage.class)) {
                entityTable1.setSupportMultiLanguage(true);
            }

            entityTable1.setEntityClassColumns(new LinkedHashSet());
            entityTable1.setEntityClassPKColumns(new LinkedHashSet());
            List fields1;
            if(config.isEnableMethodAnnotation()) {
                fields1 = FieldHelper.getAll(entityClass);
            } else {
                fields1 = FieldHelper.getFields(entityClass);
            }

            ExtensionAttribute extensionAttribute = (ExtensionAttribute)entityClass.getAnnotation(ExtensionAttribute.class);
            boolean useExt = extensionAttribute == null || !extensionAttribute.disable();
            Iterator var7 = fields1.iterator();

            while(true) {
                EntityField field;
                do {
                    if(!var7.hasNext()) {
                        if(entityTable1.getEntityClassPKColumns().isEmpty()) {
                            entityTable1.setEntityClassPKColumns(entityTable1.getEntityClassColumns());
                        }

                        entityTableMap.put(entityClass, entityTable1);
                        return;
                    }

                    field = (EntityField)var7.next();
                } while(!useExt && field.getName().matches("attribute(\\d+|Category)"));

                processField(entityTable1, style, field);
            }
        }
    }

    private static void processField(final EntityTable entityTable, Style style, final EntityField field) {
        EntityColumn entityColumn = new EntityColumn(entityTable);
        if(field.isAnnotationPresent(Transient.class)) {
            entityColumn.setSelectable(false);
            entityColumn.setInsertable(false);
            entityColumn.setUpdatable(false);
        }

        entityColumn.setProperty(field.getName());
        entityColumn.setJavaType(field.getJavaType());
        if(entityTable.isSupportMultiLanguage()) {
            if(field.isAnnotationPresent(Id.class)) {
                JoinTable jts = new JoinTable() {
                    public Class<? extends Annotation> annotationType() {
                        return JoinTable.class;
                    }

                    public String name() {
                        return "multiLanguageJoin";
                    }

                    public boolean joinMultiLanguageTable() {
                        return true;
                    }

                    public Class<?> target() {
                        return entityTable.getEntityClass();
                    }

                    public JoinType type() {
                        return JoinType.INNER;
                    }

                    public JoinOn[] on() {
                        JoinOn on1 = new JoinOn() {
                            public Class<? extends Annotation> annotationType() {
                                return JoinOn.class;
                            }

                            public String joinField() {
                                return field.getName();
                            }

                            public String joinExpression() {
                                return "";
                            }
                        };
                        JoinOn on2 = new JoinOn() {
                            public Class<? extends Annotation> annotationType() {
                                return JoinOn.class;
                            }

                            public String joinField() {
                                return "lang";
                            }

                            public String joinExpression() {
                                return "#{request.locale,jdbcType=VARCHAR,javaType=java.lang.String}";
                            }
                        };
                        return new JoinOn[]{on1, on2};
                    }
                };
                entityColumn.addJoinTable(jts);
                entityTable.createAlias(buildJoinKey(jts));
                entityTable.getJoinMapping().put(jts.name(), entityColumn);
            }

            if(field.isAnnotationPresent(MultiLanguageField.class)) {
                JoinColumn var9 = new JoinColumn() {
                    public Class<? extends Annotation> annotationType() {
                        return JoinColumn.class;
                    }

                    public String joinName() {
                        return "multiLanguageJoin";
                    }

                    public String field() {
                        return field.getName();
                    }

                    public String expression() {
                        return "";
                    }
                };
                entityColumn.setJoinColumn(var9);
                entityColumn.setSelectable(true);
            }
        }

        JoinTable[] var10 = (JoinTable[])field.getAnnotations(JoinTable.class);
        if(var10 != null) {
            JoinTable[] columnName = var10;
            int generatedValue = var10.length;

            for(int generator = 0; generator < generatedValue; ++generator) {
                JoinTable identityDialect = columnName[generator];
                entityColumn.addJoinTable(identityDialect);
                entityTable.createAlias(buildJoinKey(identityDialect));
                entityTable.getJoinMapping().put(identityDialect.name(), entityColumn);
            }
        }

        Where var11;
        if(field.isAnnotationPresent(Where.class)) {
            var11 = (Where)field.getAnnotation(Where.class);
            entityColumn.setWhere(var11);
            entityTable.getWhereColumns().add(entityColumn);
        }

        if(field.isAnnotationPresent(OrderBy.class)) {
            OrderBy var12 = (OrderBy)field.getAnnotation(OrderBy.class);
            if(!"".equals(var12.value())) {
                entityColumn.setOrderBy(var12.value());
            }

            entityTable.getSortColumns().add(entityColumn);
        }

        if(field.isAnnotationPresent(JoinColumn.class)) {
            JoinColumn var13 = (JoinColumn)field.getAnnotation(JoinColumn.class);
            entityColumn.setJoinColumn(var13);
            entityColumn.setSelectable(true);
            entityColumn.setInsertable(false);
            entityColumn.setUpdatable(false);
        }

        if(field.isAnnotationPresent(Id.class)) {
            entityColumn.setId(true);
            entityTable.getEntityClassPKColumns().add(entityColumn);
            if(entityColumn.getWhere() == null) {
                var11 = new Where() {
                    public Class<? extends Annotation> annotationType() {
                        return Where.class;
                    }

                    public Comparison comparison() {
                        return Comparison.EQUAL;
                    }

                    public String expression() {
                        return "";
                    }
                };
                entityColumn.setWhere(var11);
                entityTable.getWhereColumns().add(entityColumn);
            }
        }

        String var15 = null;
        if(field.isAnnotationPresent(Column.class)) {
            Column var14 = (Column)field.getAnnotation(Column.class);
            var15 = var14.name();
            entityColumn.setUpdatable(var14.updatable());
            entityColumn.setInsertable(var14.insertable());
        }

        if(field.isAnnotationPresent(ColumnType.class)) {
            ColumnType var16 = (ColumnType)field.getAnnotation(ColumnType.class);
            if(StringUtil.isEmpty(var15) && StringUtil.isNotEmpty(var16.column())) {
                var15 = var16.column();
            }

            if(var16.jdbcType() != JdbcType.UNDEFINED) {
                entityColumn.setJdbcType(var16.jdbcType());
            }

            if(var16.typeHandler() != UnknownTypeHandler.class) {
                entityColumn.setTypeHandler(var16.typeHandler());
            }
        } else if(field.getJavaType() == Date.class) {
            entityColumn.setJdbcType(JdbcType.TIMESTAMP);
        }

        if(StringUtil.isEmpty(var15)) {
            var15 = StringUtil.convertByStyle(field.getName(), style);
        }

        entityColumn.setColumn(var15);
        if(field.isAnnotationPresent(SequenceGenerator.class)) {
            SequenceGenerator var17 = (SequenceGenerator)field.getAnnotation(SequenceGenerator.class);
            if("".equals(var17.sequenceName())) {
                throw new RuntimeException(entityTable.getEntityClass() + "字段" + field.getName() + "的注解@SequenceGenerator未指定sequenceName!");
            }

            entityColumn.setSequenceName(var17.sequenceName());
        } else if(field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue var18 = (GeneratedValue)field.getAnnotation(GeneratedValue.class);
            String var19 = var18.generator();
            if("UUID".equals(var19)) {
                entityColumn.setUuid(true);
            } else if("JDBC".equals(var19)) {
                entityColumn.setIdentity(true);
                entityColumn.setGenerator(var19);
                entityTable.setKeyProperties(entityColumn.getProperty());
                entityTable.setKeyColumns(entityColumn.getColumn());
            } else if("SEQUENCE".equals(var19)) {
                entityColumn.setIdentity(true);
                entityColumn.setGenerator(var19);
                entityTable.setKeyProperties(entityColumn.getProperty());
                entityTable.setKeyColumns(entityColumn.getColumn());
            } else if(!"IDENTITY".equals(var19) && !"".equals(var19)) {
                if(var18.strategy() != GenerationType.IDENTITY) {
                    throw new RuntimeException(field.getName() + " - 该字段@GeneratedValue配置只允许以下几种形式:\n1.全部数据库通用的@GeneratedValue(generator=\"UUID\")\n2.useGeneratedKeys的@GeneratedValue(generator=\"JDBC\")  \n3.useGeneratedKeys的@GeneratedValue(generator=\"SEQUENCE\")  \n4.类似mysql数据库的@GeneratedValue(strategy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
                }

                entityColumn.setIdentity(true);
                if(!"".equals(var19)) {
                    IdentityDialect var20 = IdentityDialect.getDatabaseDialect(var19);
                    if(var20 != null) {
                        var19 = var20.getIdentityRetrievalStatement();
                    }

                    entityColumn.setGenerator(var19);
                }
            } else {
                entityColumn.setIdentity(true);
                entityColumn.setGenerator("IDENTITY");
                entityTable.setKeyProperties(entityColumn.getProperty());
                entityTable.setKeyColumns(entityColumn.getColumn());
            }
        }

        if(field.isAnnotationPresent(MultiLanguageField.class)) {
            entityColumn.setMultiLanguageField(true);
        }

        entityColumn.setCondition((Condition)field.getAnnotation(Condition.class));
        if(!field.isAnnotationPresent(Transient.class)) {
            entityTable.getEntityClassColumns().add(entityColumn);
        }

        if(entityColumn.isSelectable()) {
            entityTable.getAllColumns().add(entityColumn);
        }

    }
}
