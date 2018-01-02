package xy99.core.mybatis.util;


import xy99.core.core.annotation.MultiLanguageFiled;
import xy99.core.mybatis.entity.EntityField;
import xy99.core.mybatis.entity.Example;
import xy99.core.mybatis.entity.IDynamicTableName;
import xy99.core.system.dto.BaseDTO;
import xy99.core.system.dto.DTOClassInfo;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public abstract class OGNL {
    private static final Pattern COL_PATTERN = Pattern.compile("[\\d\\w_]+");

    public OGNL() {
    }

    public static boolean hasSelectColumns(Object parameter) {
        if(parameter != null && parameter instanceof Example) {
            Example example = (Example)parameter;
            if(example.getSelectColumns() != null && example.getSelectColumns().size() > 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasNoSelectColumns(Object parameter) {
        return !hasSelectColumns(parameter);
    }

    public static boolean isDynamicParameter(Object parameter) {
        return parameter != null && parameter instanceof IDynamicTableName;
    }

    public static boolean isNotDynamicParameter(Object parameter) {
        return !isDynamicParameter(parameter);
    }

    public static String getOrderByClause(Object parameter) {
        if(parameter == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder(64);
            if(parameter instanceof BaseDTO) {
                String sortName = ((BaseDTO)parameter).getSortname();
                EntityField[] ids = DTOClassInfo.getIdFields(parameter.getClass());
                if(StringUtil.isNotEmpty(sortName)) {
                    if(!COL_PATTERN.matcher(sortName).matches()) {
                        throw new RuntimeException("Invalid sortname:" + sortName);
                    }

                    String order = ((BaseDTO)parameter).getSortorder();
                    if(!"ASC".equalsIgnoreCase(order) && !"DESC".equalsIgnoreCase(order) && order != null) {
                        throw new RuntimeException("Invalid sortorder:" + order);
                    }

                    String columnName = unCamel(sortName);
                    sb.append(columnName).append(" ");
                    sb.append(StringUtils.defaultIfEmpty(order, "ASC"));
                    if(ids.length > 0 && !ids[0].getName().equals(sortName)) {
                        sb.append(",").append(DTOClassInfo.getColumnName(ids[0])).append(" ASC");
                    }
                } else if(ids.length > 0) {
                    sb.append(DTOClassInfo.getColumnName(ids[0])).append(" ASC");
                }
            }

            return StringUtils.trimToNull(sb.toString());
        }
    }

    public static String getOrderByClause_TL(Object parameter) {
        if(parameter == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder(64);
            if(parameter instanceof BaseDTO) {
                String sortName = ((BaseDTO)parameter).getSortname();
                EntityField[] ids = DTOClassInfo.getIdFields(parameter.getClass());
                if(StringUtil.isNotEmpty(sortName)) {
                    if(!COL_PATTERN.matcher(sortName).matches()) {
                        throw new RuntimeException("Invalid sortname:" + sortName);
                    }

                    String order = ((BaseDTO)parameter).getSortorder();
                    if(!"ASC".equalsIgnoreCase(order) && !"DESC".equalsIgnoreCase(order) && order != null) {
                        throw new RuntimeException("Invalid sortorder:" + order);
                    }

                    String columnName = unCamel(sortName);
                    EntityField[] mlfs = DTOClassInfo.getMultiLanguageFields(parameter.getClass());
                    EntityField[] var7 = mlfs;
                    int var8 = mlfs.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        EntityField f = var7[var9];
                        if(f.getName().equals(columnName)) {
                            if(f.getAnnotation(MultiLanguageFiled.class) == null) {
                                sb.append("b.");
                            } else {
                                sb.append("t.");
                            }
                            break;
                        }
                    }

                    sb.append(columnName).append(" ");
                    sb.append(StringUtils.defaultIfEmpty(order, "ASC"));
                    if(ids.length > 0 && !ids[0].getName().equals(sortName)) {
                        sb.append(",b.").append(DTOClassInfo.getColumnName(ids[0])).append(" ASC");
                    }
                } else if(ids.length > 0) {
                    sb.append("b.").append(DTOClassInfo.getColumnName(ids[0])).append(" ASC");
                }
            }

            return StringUtils.trimToNull(sb.toString());
        }
    }

    public static boolean isEmpty(Object obj) {
        return obj == null?true:(obj instanceof String?((String)obj).isEmpty():(obj instanceof Collection?((Collection)obj).isEmpty():(obj instanceof Map?((Map)obj).isEmpty():(obj.getClass().isArray()?((Object[])((Object[])obj)).length == 0:false))));
    }

    public static String unCamel(String str) {
        return DTOClassInfo.camelToUnderLine(str);
    }
}
