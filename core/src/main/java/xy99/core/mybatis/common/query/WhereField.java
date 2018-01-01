package xy99.core.mybatis.common.query;


import xy99.core.mybatis.common.query.Comparison;

public class WhereField {
    private String field;
    private Comparison comparison;

    public WhereField(String field) {
        this.setField(field);
    }

    public WhereField(String field, Comparison comparison) {
        this.setField(field);
        this.setComparison(comparison);
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Comparison getComparison() {
        return this.comparison;
    }

    public void setComparison(Comparison comparison) {
        this.comparison = comparison;
    }
}
