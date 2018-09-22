package raydar.research.model;

/**
 * Created by azhar.altaf on 9/15/18.
 */

/**
 * Created by azhar.altaf on 9/15/18.
 */
public enum Operator {
    LESS_THAN("lt"),
    LESS_THAN_EQUAL("lte"),
    GREATER_THAN("gt"),
    GREATER_EQUAL("ge"),
    EQUAL("eq"),
    NOT_EQUAL("ne");

    private String code;

    Operator(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}