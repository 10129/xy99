package xy99.core.mybatis.mapperhelper;


import xy99.core.mybatis.annotation.Condition;
import xy99.core.mybatis.common.Criteria;
import xy99.core.mybatis.common.query.Comparison;
import xy99.core.mybatis.common.query.JoinColumn;
import xy99.core.mybatis.common.query.JoinOn;
import xy99.core.mybatis.common.query.JoinTable;
import xy99.core.mybatis.common.query.Selection;
import xy99.core.mybatis.common.query.SortField;
import xy99.core.mybatis.common.query.Where;
import xy99.core.mybatis.common.query.WhereField;
import xy99.core.mybatis.entity.EntityColumn;
import xy99.core.mybatis.entity.EntityField;
import xy99.core.mybatis.entity.EntityTable;
import xy99.core.mybatis.entity.IDynamicTableName;
import xy99.core.mybatis.mapperhelper.EntityHelper;
import xy99.core.mybatis.util.StringUtil;
import xy99.core.system.dto.BaseDTO;
import xy99.core.system.dto.DTOClassInfo;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import javax.persistence.Table;
import javax.persistence.criteria.JoinType;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlHelper {
    private static Logger logger = LoggerFactory.getLogger(SqlHelper.class);

    public SqlHelper() {
    }

    public static String getDynamicTableName(Class<?> entityClass, String tableName) {
        return IDynamicTableName.class.isAssignableFrom(entityClass)?"<if test=\"@xy99.core.mybatis.util.OGNL@isDynamicParameter(_parameter) and dynamicTableName != null and dynamicTableName != \'\'\">\n${dynamicTableName}\n</if>\n<if test=\"@xy99.core.mybatis.util.OGNL@isNotDynamicParameter(_parameter) or dynamicTableName == null or dynamicTableName == \'\'\">\n" + tableName + "\n</if>":tableName;
    }

    public static String getDynamicTableName(Class<?> entityClass, String tableName, String parameterName) {
        return IDynamicTableName.class.isAssignableFrom(entityClass)?(StringUtil.isNotEmpty(parameterName)?"<if test=\"@xy99.core.mybatis.util.OGNL@isDynamicParameter(" + parameterName + ") and " + parameterName + ".dynamicTableName != null and " + parameterName + ".dynamicTableName != \'\'\">\n${" + parameterName + ".dynamicTableName}\n</if>\n<if test=\"@xy99.core.mybatis.util.OGNL@isNotDynamicParameter(" + parameterName + ") or " + parameterName + ".dynamicTableName == null or " + parameterName + ".dynamicTableName == \'\'\">\n" + tableName + "\n</if>":getDynamicTableName(entityClass, tableName)):tableName;
    }

    public static String getBindCache(EntityColumn column) {
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"");
        sql.append(column.getProperty()).append("_cache\" ");
        sql.append("value=\"").append(column.getProperty()).append("\"/>");
        return sql.toString();
    }

    public static String getBindValue(EntityColumn column, String value) {
        StringBuilder sql = new StringBuilder();
        sql.append("<bind name=\"");
        sql.append(column.getProperty()).append("_bind\" ");
        sql.append("value=\'").append(value).append("\'/>");
        return sql.toString();
    }

    public static String getIfCacheNotNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache != null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    public static String getIfCacheIsNull(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"").append(column.getProperty()).append("_cache == null\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    public static String getIfNotNull(EntityColumn column, String contents, boolean empty) {
        return getIfNotNull((String)null, column, contents, empty);
    }

    public static String getIfIsNull(EntityColumn column, String contents, boolean empty) {
        return getIfIsNull((String)null, column, contents, empty);
    }

    public static String getIfNotNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if(StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }

        sql.append(column.getProperty()).append(" != null");
        if(empty && column.getJavaType().equals(String.class)) {
            sql.append(" and ");
            if(StringUtil.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }

            sql.append(column.getProperty()).append(" != \'\' ");
        }

        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    public static String getIfNotNullWithOptions(EntityColumn column, String contents) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        sql.append("null== criteria || null == criteria.updateFields || criteria.updateFields.isEmpty() || ");
        sql.append("criteria.updateFields").append(".contains(\'");
        sql.append(column.getProperty()).append("\')");
        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    public static String getIfIsNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"");
        if(StringUtil.isNotEmpty(entityName)) {
            sql.append(entityName).append(".");
        }

        sql.append(column.getProperty()).append(" == null");
        if(empty && column.getJavaType().equals(String.class)) {
            sql.append(" or ");
            if(StringUtil.isNotEmpty(entityName)) {
                sql.append(entityName).append(".");
            }

            sql.append(column.getProperty()).append(" == \'\' ");
        }

        sql.append("\">");
        sql.append(contents);
        sql.append("</if>");
        return sql.toString();
    }

    public static String getAllColumns(Class<?> entityClass) {
        Set columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        Iterator var3 = columnList.iterator();

        while(var3.hasNext()) {
            EntityColumn entityColumn = (EntityColumn)var3.next();
            sql.append(entityColumn.getColumn()).append(",");
        }

        return sql.substring(0, sql.length() - 1);
    }

    public static String selectAllColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    public static String getAllColumns_TL(Class<?> entityClass) {
        Set columnList = EntityHelper.getColumns(entityClass);
        StringBuilder sql = new StringBuilder();
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);

        EntityColumn entityColumn;
        for(Iterator var4 = columnList.iterator(); var4.hasNext(); sql.append(entityColumn.getColumn()).append(",")) {
            entityColumn = (EntityColumn)var4.next();
            if(entityTable.isSupportMultiLanguage()) {
                sql.append(entityColumn.isMultiLanguageField()?"t.":"b.");
            }
        }

        return sql.substring(0, sql.length() - 1);
    }

    public static String selectAllColumns_TL(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(getAllColumns_TL(entityClass));
        sql.append(" ");
        return sql.toString();
    }

    public static String selectCount(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        Set pkColumns = EntityHelper.getPKColumns(entityClass);
        if(pkColumns.size() == 1) {
            sql.append("COUNT(").append(((EntityColumn)pkColumns.iterator().next()).getColumn()).append(") ");
        } else {
            sql.append("COUNT(*) ");
        }

        return sql.toString();
    }

    public static String fromTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    public static String fromTable_TL(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append(" FROM ");
        String tableName = ((Table)entityClass.getAnnotation(Table.class)).name();
        EntityField[] ids = DTOClassInfo.getIdFields(entityClass);
        sql.append(tableName).append(" b ");
        sql.append("LEFT OUTER JOIN ").append(tableName.substring(0, tableName.length() - 2) + "_TL t ");
        sql.append("ON (");
        EntityField[] var5 = ids;
        int var6 = ids.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            EntityField f = var5[var7];
            String columnName = DTOClassInfo.getColumnName(f);
            sql.append("b.").append(columnName).append("=t.").append(columnName).append(" AND ");
        }

        sql.append("t.LANG=#{request.locale,jdbcType=VARCHAR,javaType=java.lang.String}");
        sql.append(")");
        sql.append(" ");
        return sql.toString();
    }

    public static String updateTable(Class<?> entityClass, String defaultTableName) {
        return updateTable(entityClass, defaultTableName, (String)null);
    }

    public static String updateTable(Class<?> entityClass, String defaultTableName, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(getDynamicTableName(entityClass, defaultTableName, entityName));
        sql.append(" ");
        return sql.toString();
    }

    public static String deleteFromTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    public static String insertIntoTable(Class<?> entityClass, String defaultTableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(getDynamicTableName(entityClass, defaultTableName));
        sql.append(" ");
        return sql.toString();
    }

    public static String insertColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        Set columnList = EntityHelper.getColumns(entityClass);
        Iterator var6 = columnList.iterator();

        while(true) {
            EntityColumn column;
            do {
                do {
                    if(!var6.hasNext()) {
                        sql.append("</trim>");
                        return sql.toString();
                    }

                    column = (EntityColumn)var6.next();
                } while(!column.isInsertable());
            } while(skipId && column.isId());

            if(notNull) {
                sql.append(getIfNotNull(column, column.getColumn() + ",", notEmpty));
            } else {
                sql.append(column.getColumn() + ",");
            }
        }
    }

    public static String insertValuesColumns(Class<?> entityClass, boolean skipId, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\">");
        Set columnList = EntityHelper.getColumns(entityClass);
        Iterator var6 = columnList.iterator();

        while(true) {
            EntityColumn column;
            do {
                do {
                    if(!var6.hasNext()) {
                        sql.append("</trim>");
                        return sql.toString();
                    }

                    column = (EntityColumn)var6.next();
                } while(!column.isInsertable());
            } while(skipId && column.isId());

            if(notNull) {
                sql.append(getIfNotNull(column, column.getColumnHolder() + ",", notEmpty));
            } else {
                sql.append(column.getColumnHolder() + ",");
            }
        }
    }

    public static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        Set columnList = EntityHelper.getColumns(entityClass);
        Iterator var6 = columnList.iterator();

        while(true) {
            while(var6.hasNext()) {
                EntityColumn column = (EntityColumn)var6.next();
                if(!column.isId() && column.isUpdatable()) {
                    if("last_update_date".equalsIgnoreCase(column.getColumn())) {
                        sql.append(column.getColumn()).append("=").append("CURRENT_TIMESTAMP,");
                    } else if(notNull) {
                        sql.append(getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                    } else {
                        sql.append(column.getColumnEqualsHolder(entityName) + ",");
                    }
                } else if("object_version_number".equalsIgnoreCase(column.getColumn())) {
                    sql.append(column.getColumn()).append("=").append(column.getColumn()).append("+1,");
                }
            }

            sql.append("</set>");
            return sql.toString();
        }
    }

    public static String updateSetColumnsWithOption(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        Set columnList = EntityHelper.getColumns(entityClass);
        Iterator var3 = columnList.iterator();

        while(true) {
            while(var3.hasNext()) {
                EntityColumn column = (EntityColumn)var3.next();
                if(!column.isId() && column.isUpdatable()) {
                    if("last_update_date".equalsIgnoreCase(column.getColumn())) {
                        sql.append(column.getColumn()).append("=").append("CURRENT_TIMESTAMP,");
                    } else {
                        sql.append(getIfNotNullWithOptions(column, column.getColumnEqualsHolder("dto") + ","));
                    }
                } else if("object_version_number".equalsIgnoreCase(column.getColumn())) {
                    sql.append(column.getColumn()).append("=").append(column.getColumn()).append("+1,");
                }
            }

            sql.append("</set>");
            return sql.toString();
        }
    }

    public static String wherePKColumns(Class<?> entityClass) {
        return wherePKColumns(entityClass, (String)null);
    }

    public static String wherePKColumns(Class<?> entityClass, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        Set columnList = EntityHelper.getPKColumns(entityClass);
        Iterator var4 = columnList.iterator();

        while(var4.hasNext()) {
            EntityColumn column = (EntityColumn)var4.next();
            sql.append(" AND " + column.getColumnEqualsHolder(entityName));
        }

        sql.append("</where>");
        return sql.toString();
    }

    public static String wherePKColumns_TL(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        Set columnList = EntityHelper.getPKColumns(entityClass);
        Iterator var3 = columnList.iterator();

        while(var3.hasNext()) {
            EntityColumn column = (EntityColumn)var3.next();
            sql.append(" AND b." + column.getColumnEqualsHolder());
        }

        sql.append("</where>");
        return sql.toString();
    }

    public static String whereAllIfColumns(Class<?> entityClass, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        Set columnList = EntityHelper.getColumns(entityClass);
        Iterator var4 = columnList.iterator();

        while(var4.hasNext()) {
            EntityColumn column = (EntityColumn)var4.next();
            Condition condition = column.getCondition();
            if(condition == null) {
                sql.append(getIfNotNull(column, " AND " + column.getColumnEqualsHolder(), empty));
            } else if(!condition.exclude()) {
                sql.append(getIfNotNull(column, " AND " + column.getColumnHolderWithOperator(condition.operator(), condition.autoWrap()), empty));
            }
        }

        sql.append("</where>");
        return sql.toString();
    }

    public static String whereAllIfColumns_TL(Class<?> entityClass, boolean empty, boolean equals) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        Set columnList = EntityHelper.getColumns(entityClass);
        Iterator var5 = columnList.iterator();

        while(var5.hasNext()) {
            EntityColumn column = (EntityColumn)var5.next();
            if(column.isMultiLanguageField()) {
                sql.append(getIfNotNull(column, " AND t." + (equals?column.getColumnEqualsHolder():column.getColumnLikeHolder()), empty));
            } else {
                Condition condition = column.getCondition();
                if(condition == null) {
                    sql.append(getIfNotNull(column, " AND b." + column.getColumnEqualsHolder(), empty));
                } else if(!condition.exclude()) {
                    sql.append(getIfNotNull(column, " AND b." + column.getColumnHolderWithOperator(condition.operator(), condition.autoWrap()), empty));
                }
            }
        }

        sql.append("</where>");
        return sql.toString();
    }

    public static String orderByDefault(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        if(BaseDTO.class.isAssignableFrom(entityClass)) {
            sql.append("<bind name=\"__orderByClause\" value=\"@xy99.core.mybatis.util.OGNL@getOrderByClause(_parameter)\"/>");
            sql.append("<if test=\"__orderByClause!=null\">");
            sql.append("ORDER BY ${__orderByClause}");
            sql.append("</if>");
        } else {
            EntityField[] idField = DTOClassInfo.getIdFields(entityClass);
            if(idField.length > 0) {
                sql.append("ORDER BY ").append(DTOClassInfo.getColumnName(idField[0])).append(" ASC");
            }
        }

        return sql.toString();
    }

    public static String orderByDefault_TL(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        if(BaseDTO.class.isAssignableFrom(entityClass)) {
            sql.append("<bind name=\"__orderByClause\" value=\"@xy99.core.mybatis.util.OGNL@getOrderByClause_TL(_parameter)\"/>");
            sql.append("<if test=\"__orderByClause!=null\">");
            sql.append("ORDER BY ${__orderByClause}");
            sql.append("</if>");
        } else {
            EntityField[] idField = DTOClassInfo.getIdFields(entityClass);
            if(idField.length > 0) {
                sql.append("ORDER BY b.").append(DTOClassInfo.getColumnName(idField[0])).append(" ASC");
            }
        }

        return sql.toString();
    }

    public static String exampleSelectColumns(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"@xy99.core.mybatis.util.OGNL@hasSelectColumns(_parameter)\">");
        sql.append("<foreach collection=\"_parameter.selectColumns\" item=\"selectColumn\" separator=\",\">");
        sql.append("${selectColumn}");
        sql.append("</foreach>");
        sql.append("</if>");
        sql.append("<if test=\"@xy99.core.mybatis.util.OGNL@hasNoSelectColumns(_parameter)\">");
        sql.append(getAllColumns(entityClass));
        sql.append("</if>");
        return sql.toString();
    }

    public static String exampleOrderBy(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"orderByClause != null\">");
        sql.append("order by ${orderByClause}");
        sql.append("</if>");
        String orderByClause = EntityHelper.getOrderByClause(entityClass);
        if(orderByClause.length() > 0) {
            sql.append("<if test=\"orderByClause == null\">");
            sql.append("ORDER BY " + orderByClause);
            sql.append("</if>");
        }

        return sql.toString();
    }

    public static String exampleWhereClause() {
        return "<if test=\"_parameter != null\"><where>\n  <foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\">\n    <if test=\"criteria.valid\">\n      <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n          <choose>\n            <when test=\"criterion.noValue\">\n              and ${criterion.condition}\n            </when>\n            <when test=\"criterion.singleValue\">\n              and ${criterion.condition} #{criterion.value}\n            </when>\n            <when test=\"criterion.betweenValue\">\n              and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n            </when>\n            <when test=\"criterion.listValue\">\n              and ${criterion.condition}\n              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n                #{listItem}\n              </foreach>\n            </when>\n          </choose>\n        </foreach>\n      </trim>\n    </if>\n  </foreach>\n</where></if>";
    }

    public static String updateByExampleWhereClause() {
        return "<where>\n  <foreach collection=\"example.oredCriteria\" item=\"criteria\" separator=\"or\">\n    <if test=\"criteria.valid\">\n      <trim prefix=\"(\" prefixOverrides=\"and\" suffix=\")\">\n        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n          <choose>\n            <when test=\"criterion.noValue\">\n              and ${criterion.condition}\n            </when>\n            <when test=\"criterion.singleValue\">\n              and ${criterion.condition} #{criterion.value}\n            </when>\n            <when test=\"criterion.betweenValue\">\n              and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n            </when>\n            <when test=\"criterion.listValue\">\n              and ${criterion.condition}\n              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n                #{listItem}\n              </foreach>\n            </when>\n          </choose>\n        </foreach>\n      </trim>\n    </if>\n  </foreach>\n</where>";
    }

    public static String buildSelectByPrimaryKeySQL(BaseDTO dto) {
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        Criteria criteria = new Criteria();
        Iterator var3 = table.getEntityClassPKColumns().iterator();

        while(var3.hasNext()) {
            EntityColumn pkColumn = (EntityColumn)var3.next();
            criteria.where(new String[]{pkColumn.getProperty()});
        }

        return buildSelectSelectiveSql(dto, criteria);
    }

    public static String buildSelectSelectiveSql(final BaseDTO dto, final Criteria criteria) {
        final EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        final ArrayList selectFields = new ArrayList(50);
        List selections = criteria.getSelectFields();
        Iterator sql;
        if(selections != null && !selections.isEmpty()) {
            sql = selections.iterator();

            label29:
            while(true) {
                Selection selection1;
                do {
                    if(!sql.hasNext()) {
                        break label29;
                    }

                    selection1 = (Selection)sql.next();
                } while(criteria.getExcludeSelectFields() != null && criteria.getExcludeSelectFields().contains(selection1.getField()));

                selectFields.add(selection1);
            }
        } else {
            sql = table.getAllColumns().iterator();

            label40:
            while(true) {
                EntityColumn selection;
                do {
                    if(!sql.hasNext()) {
                        break label40;
                    }

                    selection = (EntityColumn)sql.next();
                } while(criteria.getExcludeSelectFields() != null && criteria.getExcludeSelectFields().contains(selection.getProperty()));

                selectFields.add(new Selection(selection.getProperty()));
            }
        }

        String sql1 = ((StringBuilder)(new SQL() {
            {
                Iterator whereSql = selectFields.iterator();

                while(whereSql.hasNext()) {
                    Selection sortFields = (Selection)whereSql.next();
                    String column = SqlHelper.generateSelectionSQL(dto, sortFields);
                    if(StringUtils.isNotEmpty(column)) {
                        this.SELECT(column);
                    }
                }

                this.FROM(table.getName() + " " + table.getAlias());
                whereSql = table.getJoinMapping().entrySet().iterator();

                while(whereSql.hasNext()) {
                    Entry sortFields1 = (Entry)whereSql.next();
                    EntityColumn column1 = (EntityColumn)sortFields1.getValue();
                    JoinTable sortColumn = column1.findJoinTableByName((String)sortFields1.getKey());
                    String joinSql = SqlHelper.generateJoinSQL(dto, column1, sortColumn, selectFields);
                    if(StringUtils.isNotBlank(joinSql)) {
//                        JoinType sortColumn1 = sortColumn.type();
//                        switch(null.$SwitchMap$javax$persistence$criteria$JoinType[sortColumn1.ordinal()]) {
//                            case 1:
//                                this.LEFT_OUTER_JOIN(joinSql);
//                                break;
//                            case 2:
//                                this.INNER_JOIN(joinSql);
//                                break;
//                            case 3:
//                                this.RIGHT_OUTER_JOIN(joinSql);
//                        }
                    }
                }

                String whereSql1 = SqlHelper.generateWhereClauseSQL(dto, criteria);
                if(StringUtils.isNotBlank(whereSql1)) {
                    this.WHERE(whereSql1);
                }

                List sortFields2 = criteria.getSortFields();
                Iterator column2;
                if(sortFields2 != null && !sortFields2.isEmpty()) {
                    column2 = sortFields2.iterator();

                    while(true) {
                        while(column2.hasNext()) {
                            SortField sortColumn3 = (SortField)column2.next();
                            Iterator joinSql1 = table.getSortColumns().iterator();

                            while(joinSql1.hasNext()) {
                                EntityColumn sortColumn4 = (EntityColumn)joinSql1.next();
                                if(sortColumn4.getProperty().equals(sortColumn3.getField())) {
                                    this.ORDER_BY(SqlHelper.findColumnNameByField(dto, sortColumn3.getField(), false) + sortColumn3.getSortType().sql());
                                    break;
                                }
                            }
                        }

//                        return;
                    }
                } else {
                    column2 = table.getSortColumns().iterator();

                    while(column2.hasNext()) {
                        EntityColumn sortColumn2 = (EntityColumn)column2.next();
                        if(sortColumn2.getOrderBy() != null) {
                            this.ORDER_BY(SqlHelper.findColumnNameByField(dto, sortColumn2, false) + " " + sortColumn2.getOrderBy());
                        }
                    }

                }
            }
        }).usingAppender(new StringBuilder())).toString();
        return sql1;
    }

    private static String generateSelectionSQL(BaseDTO dto, Selection selection) {
        return findColumnNameByField(dto, selection.getField(), true);
    }

    private static String findColumnNameByField(BaseDTO dto, String field, boolean withAlias) {
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        EntityColumn entityColumn = table.findColumnByProperty(field);
        return findColumnNameByField(dto, entityColumn, withAlias);
    }

    private static String findColumnNameByField(BaseDTO dto, EntityColumn entityColumn, boolean withAlias) {
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        StringBuilder sb = new StringBuilder();
        if(entityColumn != null) {
            JoinColumn jc = entityColumn.getJoinColumn();
            if(jc != null) {
                EntityColumn joinField = (EntityColumn)table.getJoinMapping().get(jc.joinName());
                JoinTable joinTable = joinField.findJoinTableByName(jc.joinName());
                if(joinField != null && joinTable != null) {
                    EntityTable joinEntityTable = EntityHelper.getEntityTable(joinTable.target());
                    EntityColumn refColumn = joinEntityTable.findColumnByProperty(jc.field());
                    sb.append(table.getAlias(EntityHelper.buildJoinKey(joinTable))).append(".").append(refColumn.getColumn());
                    if(withAlias) {
                        sb.append(" AS ").append(entityColumn.getColumn());
                    }
                }
            } else {
                sb.append(table.getAlias()).append(".").append(entityColumn.getColumn());
            }
        }

        return sb.toString();
    }

    private static String generateJoinSQL(BaseDTO dto, EntityColumn localColumn, JoinTable joinTable, List<Selection> selections) {
        StringBuilder sb = new StringBuilder();
        EntityTable localTable = EntityHelper.getEntityTable(dto.getClass());
        String joinKey = EntityHelper.buildJoinKey(joinTable);
        EntityTable foreignTable = EntityHelper.getEntityTable(joinTable.target());
        boolean foundJoinColumn = false;
        Iterator jointTableName = selections.iterator();

        while(jointTableName.hasNext()) {
            Selection joinOns = (Selection)jointTableName.next();
            EntityColumn i = localTable.findColumnByProperty(joinOns.getField());
            if(i != null && i.getJoinColumn() != null && joinTable.name().equals(i.getJoinColumn().joinName())) {
                foundJoinColumn = true;
                break;
            }
        }

        if(foundJoinColumn) {
            String var17 = foreignTable.getName();
            if(joinTable.joinMultiLanguageTable()) {
                var17 = var17.substring(0, var17.length() - 2) + "_TL";
            }

            sb.append(var17).append(" ").append(localTable.getAlias(joinKey)).append(" ON ");
            JoinOn[] var18 = joinTable.on();
            int var19 = 0;

            for(int j = var18.length; var19 < j; ++var19) {
                JoinOn joinOn = var18[var19];
                String joinField = joinOn.joinField();
                if(!StringUtils.isEmpty(joinField)) {
                    if(var19 != 0) {
                        sb.append(" AND ");
                    }

                    EntityColumn foreignColumn = foreignTable.findColumnByProperty(joinField);
                    String columnName = foreignColumn != null?foreignColumn.getColumn():StringUtil.camelhumpToUnderline(joinField);
                    if(StringUtils.isEmpty(joinOn.joinExpression())) {
                        sb.append(localTable.getAlias()).append(".").append(localColumn.getColumn()).append(" = ");
                        sb.append(localTable.getAlias(joinKey)).append(".").append(columnName);
                    } else {
                        sb.append(localTable.getAlias(joinKey)).append(".").append(columnName);
                        sb.append(" = ").append(joinOn.joinExpression());
                    }
                }
            }
        }

        return sb.toString();
    }

    private static String generateWhereClauseSQL(BaseDTO dto, Criteria criteria) {
        StringBuilder sb = new StringBuilder();
        List whereFields = criteria.getWhereFields();
        EntityTable table = EntityHelper.getEntityTable(dto.getClass());
        Iterator var5 = table.getWhereColumns().iterator();

        while(var5.hasNext()) {
            EntityColumn column = (EntityColumn)var5.next();

            try {
                if(BeanUtils.getProperty(dto, column.getProperty()) != null) {
                    Where e = column.getWhere();
                    Comparison comparison = e.comparison();
                    boolean isWhereField = false;
                    if(whereFields != null && !whereFields.isEmpty()) {
                        Iterator columnName = whereFields.iterator();

                        while(columnName.hasNext()) {
                            WhereField jc = (WhereField)columnName.next();
                            String joinField = jc.getField();
                            if(joinField != null && joinField.equals(column.getProperty())) {
                                isWhereField = true;
                                if(jc.getComparison() != null) {
                                    comparison = jc.getComparison();
                                }
                                break;
                            }
                        }

                        if(!isWhereField) {
                            continue;
                        }
                    }

                    if(sb.length() > 0) {
                        sb.append(" AND ");
                    }

                    String columnName1 = column.getColumn();
                    JoinColumn jc1 = column.getJoinColumn();
                    if(jc1 != null) {
                        EntityColumn joinField1 = (EntityColumn)table.getJoinMapping().get(jc1.joinName());
                        JoinTable jt = joinField1.findJoinTableByName(jc1.joinName());
                        EntityTable foreignTable = EntityHelper.getEntityTable(jt.target());
                        EntityColumn foreignColumn = foreignTable.findColumnByProperty(jc1.field());
                        columnName1 = foreignColumn.getColumn();
                        sb.append(table.getAlias(EntityHelper.buildJoinKey(jt))).append(".");
                    } else {
                        sb.append(table.getAlias()).append(".");
                    }

                    sb.append(columnName1).append(formatComparisonSQL(comparison.sql(), column.getColumnHolder("dto")));
                }
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException var16) {
                if(logger.isErrorEnabled()) {
                    logger.error(var16.getMessage(), var16);
                }
            }
        }

        return sb.toString();
    }

    private static String formatComparisonSQL(String format, String placeHolder) {
        if(format.indexOf("{0}") != -1) {
            MessageFormat mf = new MessageFormat(format);
            return mf.format(new String[]{placeHolder});
        } else {
            return format + placeHolder;
        }
    }
}
