package raydar.research.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by azhar.altaf on 9/15/18.
 */
public class JSONResponse implements Serializable {

    private String message;
    private List<Employee> employees;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}