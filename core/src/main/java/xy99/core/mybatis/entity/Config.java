package xy99.core.mybatis.entity;


import xy99.core.mybatis.code.Style;

import java.util.Properties;

public class Config {
    private String UUID;
    private String IDENTITY;
    private boolean BEFORE = false;
    private String seqFormat;
    private String catalog;
    private String schema;
    private String dataBaseType;
    private boolean enableMethodAnnotation;
    private boolean notEmpty = false;
    private Style style;

    public Config() {
    }

    public boolean isBEFORE() {
        return this.BEFORE;
    }

    public void setBEFORE(boolean BEFORE) {
        this.BEFORE = BEFORE;
    }

    public void setOrder(String order) {
        this.BEFORE = "BEFORE".equalsIgnoreCase(order);
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getIDENTITY() {
        return StringUtil.isNotEmpty(this.IDENTITY)?this.IDENTITY:IdentityDialect.MYSQL.getIdentityRetrievalStatement();
    }

    public void setIDENTITY(String IDENTITY) {
        IdentityDialect identityDialect = IdentityDialect.getDatabaseDialect(IDENTITY);
        if(identityDialect != null) {
            this.IDENTITY = identityDialect.getIdentityRetrievalStatement();
        } else {
            this.IDENTITY = IDENTITY;
        }

    }

    public String getSchema() {
        return this.schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getSeqFormat() {
        return StringUtil.isNotEmpty(this.seqFormat)?this.seqFormat:"{0}.nextval";
    }

    public void setSeqFormat(String seqFormat) {
        this.seqFormat = seqFormat;
    }

    public String getUUID() {
        return StringUtil.isNotEmpty(this.UUID)?this.UUID:"@java.util.UUID@randomUUID().toString().replace(\"-\", \"\")";
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public boolean isNotEmpty() {
        return this.notEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        this.notEmpty = notEmpty;
    }

    public Style getStyle() {
        return this.style == null?Style.camelhump:this.style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public String getDataBaseType() {
        return this.dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public boolean isEnableMethodAnnotation() {
        return this.enableMethodAnnotation;
    }

    public void setEnableMethodAnnotation(boolean enableMethodAnnotation) {
        this.enableMethodAnnotation = enableMethodAnnotation;
    }

    public String getPrefix() {
        return StringUtil.isNotEmpty(this.catalog)?this.catalog:(StringUtil.isNotEmpty(this.schema)?this.schema:"");
    }

    public void setProperties(Properties properties) {
        if(properties == null) {
            this.style = Style.camelhump;
        } else {
            String UUID = properties.getProperty("UUID");
            if(StringUtil.isNotEmpty(UUID)) {
                this.setUUID(UUID);
            }

            String IDENTITY = properties.getProperty("IDENTITY");
            if(StringUtil.isNotEmpty(IDENTITY)) {
                this.setIDENTITY(IDENTITY);
            }

            String dataBaseType = properties.getProperty("dataBaseType");
            if(StringUtil.isNotEmpty(dataBaseType)) {
                this.setDataBaseType(dataBaseType);
            }

            String seqFormat = properties.getProperty("seqFormat");
            if(StringUtil.isNotEmpty(seqFormat)) {
                this.setSeqFormat(seqFormat);
            }

            String catalog = properties.getProperty("catalog");
            if(StringUtil.isNotEmpty(catalog)) {
                this.setCatalog(catalog);
            }

            String schema = properties.getProperty("schema");
            if(StringUtil.isNotEmpty(schema)) {
                this.setSchema(schema);
            }

            String ORDER = properties.getProperty("ORDER");
            if(StringUtil.isNotEmpty(ORDER)) {
                this.setOrder(ORDER);
            }

            String notEmpty = properties.getProperty("notEmpty");
            if(StringUtil.isNotEmpty(notEmpty)) {
                this.notEmpty = notEmpty.equalsIgnoreCase("TRUE");
            }

            String enableMethodAnnotation = properties.getProperty("enableMethodAnnotation");
            if(StringUtil.isNotEmpty(enableMethodAnnotation)) {
                this.enableMethodAnnotation = enableMethodAnnotation.equalsIgnoreCase("TRUE");
            }

            String styleStr = properties.getProperty("style");
            if(StringUtil.isNotEmpty(styleStr)) {
                try {
                    this.style = Style.valueOf(styleStr);
                } catch (IllegalArgumentException var13) {
                    throw new RuntimeException(styleStr + "不是合法的Style值!");
                }
            } else {
                this.style = Style.camelhump;
            }

        }
    }
}

