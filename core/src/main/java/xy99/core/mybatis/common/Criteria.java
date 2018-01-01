package xy99.core.mybatis.common;


import xy99.core.mybatis.common.query.SQLField;
import xy99.core.mybatis.common.query.Selection;
import xy99.core.mybatis.common.query.SortField;
import xy99.core.mybatis.common.query.SortType;
import xy99.core.mybatis.common.query.WhereField;
import com.hand.hap.system.dto.BaseDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Criteria {
    private List<Selection> selectFields;
    private List<SortField> sortFields;
    private List<WhereField> whereFields;
    private List<String> excludeSelectFields;
    private List<String> updateFields;

    public Criteria() {
    }

    public Criteria(Object obj) {
        if(obj instanceof BaseDTO) {
            BaseDTO dto = (BaseDTO)obj;
            if(dto != null && dto.getSortname() != null) {
                this.sort(dto.getSortname(), dto.getSortorder() != null?SortType.valueOf(dto.getSortorder().toUpperCase()):SortType.ASC);
            }
        }

    }

    public Criteria sort(String field, SortType sortType) {
        if(this.sortFields == null) {
            this.sortFields = new ArrayList();
        }

        if(!this.containField(this.sortFields, field)) {
            this.sortFields.add(new SortField(field, sortType));
        }

        return this;
    }

    public Criteria select(String... fields) {
        this.excludeSelectFields = null;
        if(this.selectFields == null) {
            this.selectFields = new ArrayList(50);
        }

        if(fields.length > 0 && !this.selectFields.contains("objectVersionNumber")) {
            this.selectFields.add(new Selection("objectVersionNumber"));
        }

        String[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String field = var2[var4];
            if(!this.containField(this.selectFields, field)) {
                this.selectFields.add(new Selection(field));
            }
        }

        return this;
    }

    public Criteria unSelect(String... fields) {
        this.selectFields = null;
        if(this.excludeSelectFields == null) {
            this.excludeSelectFields = new ArrayList(50);
        }

        String[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String field = var2[var4];
            if(!this.excludeSelectFields.contains(field)) {
                this.excludeSelectFields.add(field);
            }
        }

        return this;
    }

    public Criteria where(Object... fields) {
        Object[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object field = var2[var4];
            if(field instanceof WhereField) {
                this.where(new WhereField[]{(WhereField)field});
            } else if(field instanceof String) {
                this.where(new String[]{(String)field});
            }
        }

        return this;
    }

    public Criteria where(WhereField... fields) {
        if(this.whereFields == null) {
            this.whereFields = new ArrayList(15);
        }

        WhereField[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            WhereField field = var2[var4];
            if(!this.whereFields.contains(field)) {
                this.whereFields.add(field);
            }
        }

        return this;
    }

    public Criteria where(String... fields) {
        if(this.whereFields == null) {
            this.whereFields = new ArrayList(15);
        }

        String[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String field = var2[var4];
            if(!this.whereFields.contains(field)) {
                this.whereFields.add(new WhereField(field));
            }
        }

        return this;
    }

    private boolean containField(List<? extends SQLField> list, String field) {
        boolean found = false;
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            SQLField sqlField = (SQLField)var4.next();
            if(sqlField.getField().equals(field)) {
                found = true;
                break;
            }
        }

        return found;
    }

    public void update(String... fields) {
        if(this.updateFields == null) {
            this.updateFields = new ArrayList(50);
        }

        if(fields.length > 0 && !this.updateFields.contains("lastUpdateDate")) {
            this.updateFields.addAll(Arrays.asList(new String[]{"lastUpdateDate", "lastUpdatedBy", "lastUpdateLogin"}));
        }

        String[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String field = var2[var4];
            if(!this.updateFields.contains(field)) {
                this.updateFields.add(field);
            }
        }

    }

    public List<String> getUpdateFields() {
        return this.updateFields;
    }

    public List<Selection> getSelectFields() {
        return this.selectFields;
    }

    public void setSelectFields(List<Selection> selectFields) {
        this.selectFields = selectFields;
    }

    public List<SortField> getSortFields() {
        return this.sortFields;
    }

    public void setSortFields(List<SortField> sortFields) {
        this.sortFields = sortFields;
    }

    public List<WhereField> getWhereFields() {
        return this.whereFields;
    }

    public void setWhereFields(List<WhereField> whereFields) {
        this.whereFields = whereFields;
    }

    public List<String> getExcludeSelectFields() {
        return this.excludeSelectFields;
    }

    public void setExcludeSelectFields(List<String> excludeSelectFields) {
        this.excludeSelectFields = excludeSelectFields;
    }
}
