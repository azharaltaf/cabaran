package raydar.research.service;

import raydar.research.model.Employee;

import java.io.IOException;
import java.util.List;

/**
 * Created by azhar.altaf on 9/22/18.
 */
public interface EmployeeService {

    List<Employee> getAllEmployees() throws IOException;

    void saveEmployees(List<Employee> employees) throws IOException;
}
