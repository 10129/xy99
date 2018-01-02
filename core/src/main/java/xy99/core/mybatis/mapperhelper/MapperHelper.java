package xy99.core.mybatis.mapperhelper;


import xy99.core.mybatis.entity.Config;
import xy99.core.mybatis.mapperhelper.MapperTemplate;
import xy99.core.mybatis.provider.EmptyProvider;
import xy99.core.mybatis.util.StringUtil;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;

public class MapperHelper {
    private final Map<String, Boolean> msIdSkip;
    private List<Class<?>> registerClass;
    private Map<Class<?>, MapperTemplate> registerMapper;
    private Map<String, MapperTemplate> msIdCache;
    private Config config;

    public MapperHelper() {
        this.msIdSkip = new HashMap();
        this.registerClass = new ArrayList();
        this.registerMapper = new ConcurrentHashMap();
        this.msIdCache = new HashMap();
        this.config = new Config();
    }

    public MapperHelper(Properties properties) {
        this();
        this.setProperties(properties);
    }

    public Config getConfig() {
        return this.config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    private MapperTemplate fromMapperClass(Class<?> mapperClass) {
        Method[] methods = mapperClass.getDeclaredMethods();
        Class templateClass = null;
        Class tempClass = null;
        HashSet methodSet = new HashSet();
        Method[] mapperTemplate = methods;
        int e = methods.length;

        for(int methodName = 0; methodName < e; ++methodName) {
            Method e1 = mapperTemplate[methodName];
            if(e1.isAnnotationPresent(SelectProvider.class)) {
                SelectProvider provider = (SelectProvider)e1.getAnnotation(SelectProvider.class);
                tempClass = provider.type();
                methodSet.add(e1.getName());
            } else if(e1.isAnnotationPresent(InsertProvider.class)) {
                InsertProvider var16 = (InsertProvider)e1.getAnnotation(InsertProvider.class);
                tempClass = var16.type();
                methodSet.add(e1.getName());
            } else if(e1.isAnnotationPresent(DeleteProvider.class)) {
                DeleteProvider var17 = (DeleteProvider)e1.getAnnotation(DeleteProvider.class);
                tempClass = var17.type();
                methodSet.add(e1.getName());
            } else if(e1.isAnnotationPresent(UpdateProvider.class)) {
                UpdateProvider var18 = (UpdateProvider)e1.getAnnotation(UpdateProvider.class);
                tempClass = var18.type();
                methodSet.add(e1.getName());
            }

            if(templateClass == null) {
                templateClass = tempClass;
            } else if(templateClass != tempClass) {
                throw new RuntimeException("一个通用Mapper中只允许存在一个MapperTemplate子类!");
            }
        }

        if(templateClass == null || !MapperTemplate.class.isAssignableFrom(templateClass)) {
            templateClass = EmptyProvider.class;
        }

        mapperTemplate = null;

        MapperTemplate var13;
        try {
            var13 = (MapperTemplate)templateClass.getConstructor(new Class[]{Class.class, MapperHelper.class}).newInstance(new Object[]{mapperClass, this});
        } catch (Exception var12) {
            throw new RuntimeException("实例化MapperTemplate对象失败:" + var12.getMessage());
        }

        Iterator var14 = methodSet.iterator();

        while(var14.hasNext()) {
            String var15 = (String)var14.next();

            try {
                var13.addMethodMap(var15, templateClass.getMethod(var15, new Class[]{MappedStatement.class}));
            } catch (NoSuchMethodException var11) {
                throw new RuntimeException(templateClass.getCanonicalName() + "中缺少" + var15 + "方法!");
            }
        }

        return var13;
    }

    public void registerMapper(Class<?> mapperClass) {
        if(!this.registerMapper.containsKey(mapperClass)) {
            this.registerClass.add(mapperClass);
            this.registerMapper.put(mapperClass, this.fromMapperClass(mapperClass));
        }

        Class[] interfaces = mapperClass.getInterfaces();
        if(interfaces != null && interfaces.length > 0) {
            Class[] var3 = interfaces;
            int var4 = interfaces.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Class anInterface = var3[var5];
                this.registerMapper(anInterface);
            }
        }

    }

    public void registerMapper(String mapperClass) {
        try {
            this.registerMapper(Class.forName(mapperClass));
        } catch (ClassNotFoundException var3) {
            throw new RuntimeException("注册通用Mapper[" + mapperClass + "]失败，找不到该通用Mapper!");
        }
    }

    public boolean isMapperMethod(String msId) {
        if(this.msIdSkip.get(msId) != null) {
            return ((Boolean)this.msIdSkip.get(msId)).booleanValue();
        } else {
            Iterator var2 = this.registerMapper.entrySet().iterator();

            Entry entry;
            do {
                if(!var2.hasNext()) {
                    this.msIdSkip.put(msId, Boolean.valueOf(false));
                    return false;
                }

                entry = (Entry)var2.next();
            } while(!((MapperTemplate)entry.getValue()).supportMethod(msId));

            this.msIdSkip.put(msId, Boolean.valueOf(true));
            this.msIdCache.put(msId, (MapperTemplate) entry.getValue());
            return true;
        }
    }

    public boolean isExtendCommonMapper(Class<?> mapperInterface) {
        Iterator var2 = this.registerClass.iterator();

        Class mapperClass;
        do {
            if(!var2.hasNext()) {
                return false;
            }

            mapperClass = (Class)var2.next();
        } while(!mapperClass.isAssignableFrom(mapperInterface));

        return true;
    }

    public void setSqlSource(MappedStatement ms) {
        MapperTemplate mapperTemplate = (MapperTemplate)this.msIdCache.get(ms.getId());

        try {
            if(mapperTemplate != null) {
                mapperTemplate.setSqlSource(ms);
            }

        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public void setProperties(Properties properties) {
        this.config.setProperties(properties);
        String mapper = null;
        if(properties != null) {
            mapper = properties.getProperty("mappers");
        }

        if(StringUtil.isNotEmpty(mapper)) {
            String[] mappers = mapper.split(",");
            String[] var4 = mappers;
            int var5 = mappers.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String mapperClass = var4[var6];
                if(mapperClass.length() > 0) {
                    this.registerMapper(mapperClass);
                }
            }
        }

    }

    public void ifEmptyRegisterDefaultInterface() {
        if(this.registerClass.isEmpty()) {
            this.registerMapper("Mapper");
        }

    }

    public void processConfiguration(Configuration configuration) {
        this.processConfiguration(configuration, (Class)null);
    }

    public void processConfiguration(Configuration configuration, Class<?> mapperInterface) {
        String prefix;
        if(mapperInterface != null) {
            prefix = mapperInterface.getCanonicalName();
        } else {
            prefix = "";
        }

        Iterator var4 = (new ArrayList(configuration.getMappedStatements())).iterator();

        while(var4.hasNext()) {
            Object object = var4.next();
            if(object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement)object;
                if(ms.getId().startsWith(prefix) && this.isMapperMethod(ms.getId()) && ms.getSqlSource() instanceof ProviderSqlSource) {
                    this.setSqlSource(ms);
                }
            }
        }

    }
}
