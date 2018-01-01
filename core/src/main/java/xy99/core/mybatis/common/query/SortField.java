package xy99.core.mybatis.common.query;

/**
 * Created by hand on 2018/1/1.
 */
import xy99.core.mybatis.common.query.SQLField;
import xy99.core.mybatis.common.query.SortType;

public class SortField extends SQLField {
    private SortType sortType;

    public SortField(String field, SortType SortType) {
        super(field);
        this.sortType = SortType;
    }

    public SortType getSortType() {
        return this.sortType;
    }
}
