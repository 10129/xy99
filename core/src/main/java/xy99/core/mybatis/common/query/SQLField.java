package xy99.core.mybatis.common.query;


import org.apache.commons.lang.StringUtils;

public class SQLField {
    private String field;

    public SQLField(String field) {
        this.setField(field);
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        } else if(!(obj instanceof SQLField)) {
            return false;
        } else {
            SQLField sqlField = (SQLField)obj;
            return StringUtils.equals(this.field, sqlField.getField());
        }
    }
}
