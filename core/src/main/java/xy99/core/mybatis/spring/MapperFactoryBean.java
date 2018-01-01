package xy99.core.mybatis.spring;


import com.hand.hap.mybatis.mapperhelper.MapperHelper;

public class MapperFactoryBean<T> extends org.mybatis.spring.mapper.MapperFactoryBean<T> {
    private MapperHelper mapperHelper;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    protected void checkDaoConfig() {
        super.checkDaoConfig();
        if(this.mapperHelper.isExtendCommonMapper(this.getObjectType())) {
            this.mapperHelper.processConfiguration(this.getSqlSession().getConfiguration(), this.getObjectType());
        }

    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }
}
