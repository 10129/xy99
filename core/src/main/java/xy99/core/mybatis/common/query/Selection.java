package xy99.core.mybatis.common.query;

import xy99.core.mybatis.common.query.SQLField;

public class Selection extends SQLField {
    private String expression;

    public Selection(String field) {
        this(field, (String)null);
    }

    public Selection(String field, String expression) {
        super(field);
        this.setExpression(expression);
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
