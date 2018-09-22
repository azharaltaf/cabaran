package raydar.research.model;

/**
 * Created by azhar.altaf on 9/20/18.
 */
public enum Order {

    ASC("asc"), DESC("desc");

    private String code;

    Order(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
