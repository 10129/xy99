package xy99.core.mybatis.mapperhelper;


import xy99.core.mybatis.entity.EntityField;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;

public class FieldHelper {
    private static final FieldHelper.IFieldHelper fieldHelper;

    public FieldHelper() {
    }

    public static List<EntityField> getFields(Class<?> entityClass) {
        return fieldHelper.getFields(entityClass);
    }

    public static List<EntityField> getProperties(Class<?> entityClass) {
        return fieldHelper.getProperties(entityClass);
    }

    public static List<EntityField> getAll(Class<?> entityClass) {
        List fields = fieldHelper.getFields(entityClass);
        List properties = fieldHelper.getProperties(entityClass);
        ArrayList all = new ArrayList();
        HashSet usedSet = new HashSet();

        Iterator var5;
        EntityField property;
        for(var5 = fields.iterator(); var5.hasNext(); all.add(property)) {
            property = (EntityField)var5.next();
            Iterator var7 = properties.iterator();

            while(var7.hasNext()) {
                EntityField property1 = (EntityField)var7.next();
                if(!usedSet.contains(property1) && property.getName().equals(property1.getName())) {
                    property.copyFromPropertyDescriptor(property1);
                    usedSet.add(property1);
                    break;
                }
            }
        }

        var5 = properties.iterator();

        while(var5.hasNext()) {
            property = (EntityField)var5.next();
            if(!usedSet.contains(property)) {
                all.add(property);
            }
        }

        return all;
    }

    static {
        String version = System.getProperty("java.version");
        if(version.contains("1.8.")) {
            fieldHelper = new FieldHelper.Jdk8FieldHelper();
        } else {
            fieldHelper = new FieldHelper.Jdk6_7FieldHelper();
        }

    }

    static class Jdk6_7FieldHelper implements FieldHelper.IFieldHelper {
        Jdk6_7FieldHelper() {
        }

        public List<EntityField> getFields(Class<?> entityClass) {
            ArrayList fieldList = new ArrayList();
            this._getFields(entityClass, fieldList, this._getGenericTypeMap(entityClass), (Integer)null);
            return fieldList;
        }

        public List<EntityField> getProperties(Class<?> entityClass) {
            Map genericMap = this._getGenericTypeMap(entityClass);
            ArrayList entityFields = new ArrayList();

            BeanInfo beanInfo;
            try {
                beanInfo = Introspector.getBeanInfo(entityClass);
            } catch (IntrospectionException var11) {
                throw new RuntimeException(var11);
            }

            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var6 = descriptors;
            int var7 = descriptors.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                PropertyDescriptor desc = var6[var8];
                if(desc != null && !"class".equals(desc.getName())) {
                    EntityField entityField = new EntityField((Field)null, desc);
                    if(desc.getReadMethod() != null && desc.getReadMethod().getGenericReturnType() != null && desc.getReadMethod().getGenericReturnType() instanceof TypeVariable) {
                        entityField.setJavaType((Class)genericMap.get(((TypeVariable)desc.getReadMethod().getGenericReturnType()).getName()));
                    } else if(desc.getWriteMethod() != null && desc.getWriteMethod().getGenericParameterTypes() != null && desc.getWriteMethod().getGenericParameterTypes().length == 1 && desc.getWriteMethod().getGenericParameterTypes()[0] instanceof TypeVariable) {
                        entityField.setJavaType((Class)genericMap.get(((TypeVariable)desc.getWriteMethod().getGenericParameterTypes()[0]).getName()));
                    }

                    entityFields.add(entityField);
                }
            }

            return entityFields;
        }

        private void _getFields(Class<?> entityClass, List<EntityField> fieldList, Map<String, Class<?>> genericMap, Integer level) {
            if(fieldList == null) {
                throw new NullPointerException("fieldList参数不能为空!");
            } else {
                if(level == null) {
                    level = Integer.valueOf(0);
                }

                if(entityClass != Object.class) {
                    Field[] fields = entityClass.getDeclaredFields();
                    int index = 0;
                    Field[] superClass = fields;
                    int var8 = fields.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        Field field = superClass[var9];
                        if(!Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
                            EntityField entityField = new EntityField(field, (PropertyDescriptor)null);
                            if(field.getGenericType() != null && field.getGenericType() instanceof TypeVariable) {
                                if(genericMap == null || !genericMap.containsKey(((TypeVariable)field.getGenericType()).getName())) {
                                    throw new RuntimeException(entityClass + "字段" + field.getName() + "的泛型类型无法获取!");
                                }

                                entityField.setJavaType((Class)genericMap.get(((TypeVariable)field.getGenericType()).getName()));
                            } else {
                                entityField.setJavaType(field.getType());
                            }

                            if(level.intValue() != 0) {
                                fieldList.add(index, entityField);
                                ++index;
                            } else {
                                fieldList.add(entityField);
                            }
                        }
                    }

                    Class var12 = entityClass.getSuperclass();
                    if(var12 != null && !var12.equals(Object.class) && (var12.isAnnotationPresent(Entity.class) || !Map.class.isAssignableFrom(var12) && !Collection.class.isAssignableFrom(var12))) {
                        level = Integer.valueOf(level.intValue() + 1);
                        this._getFields(var12, fieldList, genericMap, level);
                    }

                }
            }
        }

        private Map<String, Class<?>> _getGenericTypeMap(Class<?> entityClass) {
            HashMap genericMap = new HashMap();
            if(entityClass == Object.class) {
                return genericMap;
            } else {
                Class superClass = entityClass.getSuperclass();
                if(superClass != null && !superClass.equals(Object.class) && (superClass.isAnnotationPresent(Entity.class) || !Map.class.isAssignableFrom(superClass) && !Collection.class.isAssignableFrom(superClass))) {
                    if(entityClass.getGenericSuperclass() instanceof ParameterizedType) {
                        Type[] types = ((ParameterizedType)entityClass.getGenericSuperclass()).getActualTypeArguments();
                        TypeVariable[] typeVariables = superClass.getTypeParameters();
                        if(typeVariables.length > 0) {
                            for(int i = 0; i < typeVariables.length; ++i) {
                                if(types[i] instanceof Class) {
                                    genericMap.put(typeVariables[i].getName(), (Class)types[i]);
                                }
                            }
                        }
                    }

                    genericMap.putAll(this._getGenericTypeMap(superClass));
                }

                return genericMap;
            }
        }
    }

    static class Jdk8FieldHelper implements FieldHelper.IFieldHelper {
        Jdk8FieldHelper() {
        }

        public List<EntityField> getFields(Class<?> entityClass) {
            List fields = this._getFields(entityClass, (List)null, (Integer)null);
            List properties = this.getProperties(entityClass);
            HashSet usedSet = new HashSet();
            Iterator var5 = fields.iterator();

            while(true) {
                while(var5.hasNext()) {
                    EntityField field = (EntityField)var5.next();
                    Iterator var7 = properties.iterator();

                    while(var7.hasNext()) {
                        EntityField property = (EntityField)var7.next();
                        if(!usedSet.contains(property) && field.getName().equals(property.getName())) {
                            field.setJavaType(property.getJavaType());
                            break;
                        }
                    }
                }

                return fields;
            }
        }

        private List<EntityField> _getFields(Class<?> entityClass, List<EntityField> fieldList, Integer level) {
            if(fieldList == null) {
                fieldList = new ArrayList();
            }

            if(level == null) {
                level = Integer.valueOf(0);
            }

            if(entityClass.equals(Object.class)) {
                return (List)fieldList;
            } else {
                Field[] fields = entityClass.getDeclaredFields();
                int index = 0;

                for(int superClass = 0; superClass < fields.length; ++superClass) {
                    Field field = fields[superClass];
                    if(!Modifier.isStatic(field.getModifiers())) {
                        if(level.intValue() != 0) {
                            ((List)fieldList).add(index, new EntityField(field, (PropertyDescriptor)null));
                            ++index;
                        } else {
                            ((List)fieldList).add(new EntityField(field, (PropertyDescriptor)null));
                        }
                    }
                }

                Class var8 = entityClass.getSuperclass();
                return (List)(var8 == null || var8.equals(Object.class) || !var8.isAnnotationPresent(Entity.class) && (Map.class.isAssignableFrom(var8) || Collection.class.isAssignableFrom(var8))?fieldList:this._getFields(entityClass.getSuperclass(), (List)fieldList, Integer.valueOf(level.intValue() + 1)));
            }
        }

        public List<EntityField> getProperties(Class<?> entityClass) {
            ArrayList entityFields = new ArrayList();

            BeanInfo beanInfo;
            try {
                beanInfo = Introspector.getBeanInfo(entityClass);
            } catch (IntrospectionException var9) {
                throw new RuntimeException(var9);
            }

            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var5 = descriptors;
            int var6 = descriptors.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                PropertyDescriptor desc = var5[var7];
                if(desc.getWriteMethod() != null && desc.getReadMethod() != null && !"class".equals(desc.getName())) {
                    entityFields.add(new EntityField((Field)null, desc));
                }
            }

            return entityFields;
        }
    }

    interface IFieldHelper {
        List<EntityField> getFields(Class<?> var1);

        List<EntityField> getProperties(Class<?> var1);
    }
}
