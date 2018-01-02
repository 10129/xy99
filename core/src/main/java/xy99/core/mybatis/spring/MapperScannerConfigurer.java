package xy99.core.mybatis.spring;


import xy99.core.mybatis.common.Marker;
import xy99.core.mybatis.mapperhelper.MapperHelper;
import xy99.core.mybatis.spring.MapperFactoryBean;
import xy99.core.mybatis.util.StringUtil;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class MapperScannerConfigurer extends org.mybatis.spring.mapper.MapperScannerConfigurer {
    private MapperHelper mapperHelper = new MapperHelper();
    private Map<String, String> propertiesMap;

    public MapperScannerConfigurer() {
    }

    public void setMarkerInterface(Class<?> superClass) {
        super.setMarkerInterface(superClass);
        if(Marker.class.isAssignableFrom(superClass)) {
            this.mapperHelper.registerMapper(superClass);
        }

    }

    public MapperHelper getMapperHelper() {
        return this.mapperHelper;
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    public void setProperties(Properties properties) {
        this.mapperHelper.setProperties(properties);
    }

    public void setPropertiesMap(Map<String, String> propertiesMap) {
        if(propertiesMap.get("ORDER") == null) {
            if("JDBC".equalsIgnoreCase((String)propertiesMap.get("IDENTITY"))) {
                propertiesMap.put("ORDER", "AFTER");
            } else {
                propertiesMap.put("ORDER", "BEFORE");
            }
        }

        this.propertiesMap = propertiesMap;
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        Properties config = new Properties();
        Properties p = new Properties();

        try {
            config.load(this.getClass().getResourceAsStream("/config.properties"));
            if(this.propertiesMap.get("ORDER") == null) {
                if("JDBC".equalsIgnoreCase((String)this.propertiesMap.get("IDENTITY"))) {
                    p.put("ORDER", "AFTER");
                } else {
                    p.put("ORDER", "BEFORE");
                }
            }

            this.propertiesMap.forEach((k, v) -> {
                if(v.startsWith("${") && v.endsWith("}")) {
                    p.put(k, config.getProperty(v.substring(2, v.length() - 1), v));
                } else {
                    p.put(k, v);
                }

            });
            this.setProperties(p);
        } catch (IOException var11) {
            throw new RuntimeException(var11);
        }

        super.postProcessBeanDefinitionRegistry(registry);
        this.mapperHelper.ifEmptyRegisterDefaultInterface();
        String[] names = registry.getBeanDefinitionNames();
        String[] var6 = names;
        int var7 = names.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String name = var6[var8];
            BeanDefinition beanDefinition = registry.getBeanDefinition(name);
            if(beanDefinition instanceof GenericBeanDefinition) {
                GenericBeanDefinition definition = (GenericBeanDefinition)beanDefinition;
                if(StringUtil.isNotEmpty(definition.getBeanClassName()) && definition.getBeanClassName().equals("org.mybatis.spring.mapper.MapperFactoryBean")) {
                    definition.setBeanClass(MapperFactoryBean.class);
                    definition.getPropertyValues().add("mapperHelper", this.mapperHelper);
                }
            }
        }

    }
}

