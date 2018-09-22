package raydar.research.model;

import java.io.Serializable;

/**
 * Created by azhar.altaf on 9/20/18.
 */
public class SearchCriteria implements Serializable {
    private String sort;
    private String operator;
    private int value;
    private Long id;


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
