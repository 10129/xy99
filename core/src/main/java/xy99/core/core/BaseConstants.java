package xy99.core.core;

/**
 * Created by hand on 2018/1/2.
 */

public interface BaseConstants {
    String VIEW_REDIRECT = "redirect:";
    String YES = "Y";
    String NO = "N";
    String SYSTEM_MAX_DATE = "9999/12/31 23:59:59";
    String SYSTEM_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
    String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String DATE_FORMAT = "yyyy-MM-dd";
    String CACHE_RESOURCE_URL = "resource_url";
    String CACHE_RESOURCE_ID = "resource_id";
    String CACHE_ROLE_CODE = "role";
    String CACHE_OAUTH_CLIENT = "oauth_client";
    String CACHE_PROMPT = "prompt";
    String CACHE_CODE = "code";
    String CACHE_FUNCTION = "function";
    String CACHE_DATA_PERMISSION_RULE = "data_permission_rule";
    String OPTIONS_DTO = "dto";
    String OPTIONS_CRITERIA = "criteria";
    String ROLE_RESOURCE_CACHE = "role_resource";
    String DEFAULT_LANG = "zh_CN";
    String PREFERENCE_TIME_ZONE = "timeZone";
    String PREFERENCE_LOCALE = "locale";
    String PREFERENCE_THEME = "theme";
    String PREFERENCE_AUTO_DELIVER = "autoDelegate";
    String PREFERENCE_START_DELIVER = "deliverStartDate";
    String PREFERENCE_END_DELIVER = "deliverEndDate";
    String GENERATOR_TYPE = "IDENTITY";
    String LIKE = "LIKE";
    String XML_DATA_TYPE_FUNCTION = "fn:";
    String XML_DATA_TYPE_BOOLEAN = "boolean:";
    String XML_DATA_TYPE_INTEGER = "integer:";
    String XML_DATA_TYPE_LONG = "long:";
    String XML_DATA_TYPE_FLOAT = "float:";
    String XML_DATA_TYPE_DOUBLE = "double:";
    String XML_DATA_TYPE_DATE = "date:";
    String ERROR_CODE_SESSION_TIMEOUT = "sys_session_timeout";
    String ERROR_CODE_ACCESS_DENIED = "sys_access_denied";
    String PLACEHOLDER_LOCALE = "#{request.locale,jdbcType=VARCHAR,javaType=java.lang.String}";
    String HAP_CACHE = "hap:cache:";
    String PREFERENCE_NAV = "nav";
}
