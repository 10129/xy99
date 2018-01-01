package xy99.core.mybatis.common.query;

public enum SortType {
    ASC("ASC"),
    DESC("DESC");

    private String sql;

    private SortType(String sql) {
        this.sql = sql;
    }

    public String sql() {
        return " " + this.sql + " ";
    }
}
