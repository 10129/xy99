package xy99.core.mybatis.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

public class SqlMapper {
    private final SqlMapper.MSUtils msUtils;
    private final SqlSession sqlSession;

    public SqlMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.msUtils = new SqlMapper.MSUtils(sqlSession.getConfiguration());
    }

    private <T> T getOne(List<T> list) {
        if(list.size() == 1) {
            return list.get(0);
        } else if(list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    public Map<String, Object> selectOne(String sql) {
        List list = this.selectList(sql);
        return (Map)this.getOne(list);
    }

    public Map<String, Object> selectOne(String sql, Object value) {
        List list = this.selectList(sql, value);
        return (Map)this.getOne(list);
    }

    public <T> T selectOne(String sql, Class<T> resultType) {
        List list = this.selectList(sql, resultType);
        return this.getOne((List<T>) list);
    }

    public <T> T selectOne(String sql, Object value, Class<T> resultType) {
        List list = this.selectList(sql, value, resultType);
        return this.getOne((List<T>) list);
    }

    public List<Map<String, Object>> selectList(String sql) {
        String msId = this.msUtils.select(sql);
        return this.sqlSession.selectList(msId);
    }

    public List<Map<String, Object>> selectList(String sql, Object value) {
        Class parameterType = value != null?value.getClass():null;
        String msId = this.msUtils.selectDynamic(sql, parameterType);
        return this.sqlSession.selectList(msId, value);
    }

    public <T> List<T> selectList(String sql, Class<T> resultType) {
        String msId;
        if(resultType == null) {
            msId = this.msUtils.select(sql);
        } else {
            msId = this.msUtils.select(sql, resultType);
        }

        return this.sqlSession.selectList(msId);
    }

    public <T> List<T> selectList(String sql, Object value, Class<T> resultType) {
        Class parameterType = value != null?value.getClass():null;
        String msId;
        if(resultType == null) {
            msId = this.msUtils.selectDynamic(sql, parameterType);
        } else {
            msId = this.msUtils.selectDynamic(sql, parameterType, resultType);
        }

        return this.sqlSession.selectList(msId, value);
    }

    public int insert(String sql) {
        String msId = this.msUtils.insert(sql);
        return this.sqlSession.insert(msId);
    }

    public int insert(String sql, Object value) {
        Class parameterType = value != null?value.getClass():null;
        String msId = this.msUtils.insertDynamic(sql, parameterType);
        return this.sqlSession.insert(msId, value);
    }

    public int update(String sql) {
        String msId = this.msUtils.update(sql);
        return this.sqlSession.update(msId);
    }

    public int update(String sql, Object value) {
        Class parameterType = value != null?value.getClass():null;
        String msId = this.msUtils.updateDynamic(sql, parameterType);
        return this.sqlSession.update(msId, value);
    }

    public int delete(String sql) {
        String msId = this.msUtils.delete(sql);
        return this.sqlSession.delete(msId);
    }

    public int delete(String sql, Object value) {
        Class parameterType = value != null?value.getClass():null;
        String msId = this.msUtils.deleteDynamic(sql, parameterType);
        return this.sqlSession.delete(msId, value);
    }

    private class MSUtils {
        private Configuration configuration;
        private LanguageDriver languageDriver;

        private MSUtils(Configuration configuration) {
            this.configuration = configuration;
            this.languageDriver = configuration.getDefaultScriptingLanuageInstance();
        }

        private String newMsId(String sql, SqlCommandType sqlCommandType) {
            StringBuilder msIdBuilder = new StringBuilder(sqlCommandType.toString());
            msIdBuilder.append(".").append(sql.hashCode());
            return msIdBuilder.toString();
        }

        private boolean hasMappedStatement(String msId) {
            return this.configuration.hasStatement(msId, false);
        }

        private void newSelectMappedStatement(String msId, SqlSource sqlSource, final Class<?> resultType) {
            MappedStatement ms = (new Builder(this.configuration, msId, sqlSource, SqlCommandType.SELECT)).resultMaps(new ArrayList() {
                {
                    this.add((new org.apache.ibatis.mapping.ResultMap.Builder(MSUtils.this.configuration, "defaultResultMap", resultType, new ArrayList(0))).build());
                }
            }).build();
            this.configuration.addMappedStatement(ms);
        }

        private void newUpdateMappedStatement(String msId, SqlSource sqlSource, SqlCommandType sqlCommandType) {
            MappedStatement ms = (new Builder(this.configuration, msId, sqlSource, sqlCommandType)).resultMaps(new ArrayList() {
                {
                    this.add((new org.apache.ibatis.mapping.ResultMap.Builder(MSUtils.this.configuration, "defaultResultMap", Integer.TYPE, new ArrayList(0))).build());
                }
            }).build();
            this.configuration.addMappedStatement(ms);
        }

        private String select(String sql) {
            String msId = this.newMsId(sql, SqlCommandType.SELECT);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                StaticSqlSource sqlSource = new StaticSqlSource(this.configuration, sql);
                this.newSelectMappedStatement(msId, sqlSource, Map.class);
                return msId;
            }
        }

        private String selectDynamic(String sql, Class<?> parameterType) {
            String msId = this.newMsId(sql + parameterType, SqlCommandType.SELECT);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, parameterType);
                this.newSelectMappedStatement(msId, sqlSource, Map.class);
                return msId;
            }
        }

        private String select(String sql, Class<?> resultType) {
            String msId = this.newMsId(resultType + sql, SqlCommandType.SELECT);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                StaticSqlSource sqlSource = new StaticSqlSource(this.configuration, sql);
                this.newSelectMappedStatement(msId, sqlSource, resultType);
                return msId;
            }
        }

        private String selectDynamic(String sql, Class<?> parameterType, Class<?> resultType) {
            String msId = this.newMsId(resultType + sql + parameterType, SqlCommandType.SELECT);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, parameterType);
                this.newSelectMappedStatement(msId, sqlSource, resultType);
                return msId;
            }
        }

        private String insert(String sql) {
            String msId = this.newMsId(sql, SqlCommandType.INSERT);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                StaticSqlSource sqlSource = new StaticSqlSource(this.configuration, sql);
                this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
                return msId;
            }
        }

        private String insertDynamic(String sql, Class<?> parameterType) {
            String msId = this.newMsId(sql + parameterType, SqlCommandType.INSERT);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, parameterType);
                this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.INSERT);
                return msId;
            }
        }

        private String update(String sql) {
            String msId = this.newMsId(sql, SqlCommandType.UPDATE);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                StaticSqlSource sqlSource = new StaticSqlSource(this.configuration, sql);
                this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
                return msId;
            }
        }

        private String updateDynamic(String sql, Class<?> parameterType) {
            String msId = this.newMsId(sql + parameterType, SqlCommandType.UPDATE);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, parameterType);
                this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.UPDATE);
                return msId;
            }
        }

        private String delete(String sql) {
            String msId = this.newMsId(sql, SqlCommandType.DELETE);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                StaticSqlSource sqlSource = new StaticSqlSource(this.configuration, sql);
                this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
                return msId;
            }
        }

        private String deleteDynamic(String sql, Class<?> parameterType) {
            String msId = this.newMsId(sql + parameterType, SqlCommandType.DELETE);
            if(this.hasMappedStatement(msId)) {
                return msId;
            } else {
                SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, parameterType);
                this.newUpdateMappedStatement(msId, sqlSource, SqlCommandType.DELETE);
                return msId;
            }
        }
    }
}
