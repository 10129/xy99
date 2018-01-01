package xy99.core.mybatis.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.type.TypeHandler;

public class Example implements IDynamicTableName {
    protected String orderByClause;
    protected boolean distinct;
    protected boolean exists;
    protected boolean notNull;
    protected Set<String> selectColumns;
    protected List<Example.Criteria> oredCriteria;
    protected Class<?> entityClass;
    protected EntityTable table;
    protected Map<String, EntityColumn> propertyMap;
    protected String tableName;
    protected Example.OrderBy ORDERBY;

    public Example(Class<?> entityClass) {
        this(entityClass, true);
    }

    public Example(Class<?> entityClass, boolean exists) {
        this(entityClass, exists, false);
    }

    public Example(Class<?> entityClass, boolean exists, boolean notNull) {
        this.exists = exists;
        this.notNull = notNull;
        this.oredCriteria = new ArrayList();
        this.entityClass = entityClass;
        this.table = EntityHelper.getEntityTable(entityClass);
        this.propertyMap = new HashMap(this.table.getEntityClassColumns().size());
        Iterator var4 = this.table.getEntityClassColumns().iterator();

        while(var4.hasNext()) {
            EntityColumn column = (EntityColumn)var4.next();
            this.propertyMap.put(column.getProperty(), column);
        }

        this.ORDERBY = new Example.OrderBy(this, this.propertyMap);
    }

    public Class<?> getEntityClass() {
        return this.entityClass;
    }

    public String getOrderByClause() {
        return this.orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public Example.OrderBy orderBy(String property) {
        this.ORDERBY.orderBy(property);
        return this.ORDERBY;
    }

    public Set<String> getSelectColumns() {
        return this.selectColumns;
    }

    public Example selectProperties(String... properties) {
        if(properties != null && properties.length > 0) {
            if(this.selectColumns == null) {
                this.selectColumns = new LinkedHashSet();
            }

            String[] var2 = properties;
            int var3 = properties.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String property = var2[var4];
                if(this.propertyMap.containsKey(property)) {
                    this.selectColumns.add(((EntityColumn)this.propertyMap.get(property)).getColumn());
                }
            }
        }

        return this;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public List<Example.Criteria> getOredCriteria() {
        return this.oredCriteria;
    }

    public void or(Example.Criteria criteria) {
        this.oredCriteria.add(criteria);
    }

    public Example.Criteria or() {
        Example.Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }

    public Example.Criteria createCriteria() {
        Example.Criteria criteria = this.createCriteriaInternal();
        if(this.oredCriteria.isEmpty()) {
            this.oredCriteria.add(criteria);
        }

        return criteria;
    }

    protected Example.Criteria createCriteriaInternal() {
        return new Example.Criteria(this.propertyMap, this.exists, this.notNull);
    }

    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDynamicTableName() {
        return this.tableName;
    }

    public static class Criterion {
        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;

        protected Criterion(String condition) {
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if(value instanceof Collection) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }

        }

        protected Criterion(String condition, Object value) {
            this(condition, value, (String)null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, (String)null);
        }

        public String getCondition() {
            return this.condition;
        }

        public Object getValue() {
            return this.value;
        }

        public Object getSecondValue() {
            return this.secondValue;
        }

        public boolean isNoValue() {
            return this.noValue;
        }

        public boolean isSingleValue() {
            return this.singleValue;
        }

        public boolean isBetweenValue() {
            return this.betweenValue;
        }

        public boolean isListValue() {
            return this.listValue;
        }

        public String getTypeHandler() {
            return this.typeHandler;
        }
    }

    public static class Criteria extends Example.GeneratedCriteria {
        protected Criteria(Map<String, EntityColumn> propertyMap, boolean exists, boolean notNull) {
            super(propertyMap, exists, notNull);
        }
    }

    protected abstract static class GeneratedCriteria {
        protected List<Example.Criterion> criteria;
        protected boolean exists;
        protected boolean notNull;
        protected Map<String, EntityColumn> propertyMap;

        protected GeneratedCriteria(Map<String, EntityColumn> propertyMap, boolean exists, boolean notNull) {
            this.exists = exists;
            this.notNull = notNull;
            this.criteria = new ArrayList();
            this.propertyMap = propertyMap;
        }

        private String column(String property) {
            if(this.propertyMap.containsKey(property)) {
                return ((EntityColumn)this.propertyMap.get(property)).getColumn();
            } else if(this.exists) {
                throw new RuntimeException("当前实体类不包含名为" + property + "的属性!");
            } else {
                return null;
            }
        }

        private String property(String property) {
            if(this.propertyMap.containsKey(property)) {
                return property;
            } else if(this.exists) {
                throw new RuntimeException("当前实体类不包含名为" + property + "的属性!");
            } else {
                return null;
            }
        }

        public boolean isValid() {
            return this.criteria.size() > 0;
        }

        public List<Example.Criterion> getAllCriteria() {
            return this.criteria;
        }

        public List<Example.Criterion> getCriteria() {
            return this.criteria;
        }

        protected void addCriterion(String condition) {
            if(condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            } else if(!condition.startsWith("null")) {
                this.criteria.add(new Example.Criterion(condition));
            }
        }

        protected void addCriterion(String condition, Object value, String property) {
            if(value == null) {
                if(this.notNull) {
                    throw new RuntimeException("Value for " + property + " cannot be null");
                }
            } else if(property != null) {
                this.criteria.add(new Example.Criterion(condition, value));
            }
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if(value1 != null && value2 != null) {
                if(property != null) {
                    this.criteria.add(new Example.Criterion(condition, value1, value2));
                }
            } else if(this.notNull) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
        }

        public Example.Criteria andIsNull(String property) {
            this.addCriterion(this.column(property) + " is null");
            return (Example.Criteria)this;
        }

        public Example.Criteria andIsNotNull(String property) {
            this.addCriterion(this.column(property) + " is not null");
            return (Example.Criteria)this;
        }

        public Example.Criteria andEqualTo(String property, Object value) {
            this.addCriterion(this.column(property) + " =", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andNotEqualTo(String property, Object value) {
            this.addCriterion(this.column(property) + " <>", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andGreaterThan(String property, Object value) {
            this.addCriterion(this.column(property) + " >", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andGreaterThanOrEqualTo(String property, Object value) {
            this.addCriterion(this.column(property) + " >=", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andLessThan(String property, Object value) {
            this.addCriterion(this.column(property) + " <", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andLessThanOrEqualTo(String property, Object value) {
            this.addCriterion(this.column(property) + " <=", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andIn(String property, Collection<?> values) {
            this.addCriterion(this.column(property) + " in", values, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andNotIn(String property, Collection<?> values) {
            this.addCriterion(this.column(property) + " not in", values, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andBetween(String property, Object value1, Object value2) {
            this.addCriterion(this.column(property) + " between", value1, value2, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andNotBetween(String property, Object value1, Object value2) {
            this.addCriterion(this.column(property) + " not between", value1, value2, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andLike(String property, String value) {
            this.addCriterion(this.column(property) + "  like", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andNotLike(String property, String value) {
            this.addCriterion(this.column(property) + "  not like", value, this.property(property));
            return (Example.Criteria)this;
        }

        public Example.Criteria andCondition(String condition) {
            this.addCriterion(condition);
            return (Example.Criteria)this;
        }

        public Example.Criteria andCondition(String condition, Object value) {
            this.criteria.add(new Example.Criterion(condition, value));
            return (Example.Criteria)this;
        }

        /** @deprecated */
        @Deprecated
        public Example.Criteria andCondition(String condition, Object value, String typeHandler) {
            this.criteria.add(new Example.Criterion(condition, value, typeHandler));
            return (Example.Criteria)this;
        }

        /** @deprecated */
        @Deprecated
        public Example.Criteria andCondition(String condition, Object value, Class<? extends TypeHandler> typeHandler) {
            this.criteria.add(new Example.Criterion(condition, value, typeHandler.getCanonicalName()));
            return (Example.Criteria)this;
        }

        public Example.Criteria andEqualTo(Object param) {
            MetaObject metaObject = SystemMetaObject.forObject(param);
            String[] properties = metaObject.getGetterNames();
            String[] var4 = properties;
            int var5 = properties.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String property = var4[var6];
                if(this.propertyMap.get(property) != null) {
                    Object value = metaObject.getValue(property);
                    if(value != null) {
                        this.andEqualTo(property, value);
                    }
                }
            }

            return (Example.Criteria)this;
        }
    }

    public static class OrderBy {
        private Example example;
        private Boolean isProperty;
        protected Map<String, EntityColumn> propertyMap;
        protected boolean notNull;

        public OrderBy(Example example, Map<String, EntityColumn> propertyMap) {
            this.example = example;
            this.propertyMap = propertyMap;
        }

        private String property(String property) {
            if(this.propertyMap.containsKey(property)) {
                return ((EntityColumn)this.propertyMap.get(property)).getColumn();
            } else if(this.notNull) {
                throw new RuntimeException("当前实体类不包含名为" + property + "的属性!");
            } else {
                return null;
            }
        }

        public Example.OrderBy orderBy(String property) {
            String column = this.property(property);
            if(column == null) {
                this.isProperty = Boolean.valueOf(false);
                return this;
            } else {
                if(StringUtil.isNotEmpty(this.example.getOrderByClause())) {
                    this.example.setOrderByClause(this.example.getOrderByClause() + "," + column);
                } else {
                    this.example.setOrderByClause(column);
                }

                this.isProperty = Boolean.valueOf(true);
                return this;
            }
        }

        public Example.OrderBy desc() {
            if(this.isProperty.booleanValue()) {
                this.example.setOrderByClause(this.example.getOrderByClause() + " DESC");
                this.isProperty = Boolean.valueOf(false);
            }

            return this;
        }

        public Example.OrderBy asc() {
            if(this.isProperty.booleanValue()) {
                this.example.setOrderByClause(this.example.getOrderByClause() + " ASC");
                this.isProperty = Boolean.valueOf(false);
            }

            return this;
        }
    }
}
