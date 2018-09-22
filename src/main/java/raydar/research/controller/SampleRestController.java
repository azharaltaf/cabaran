package raydar.research.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raydar.research.model.*;
import raydar.research.service.EmployeeService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by azhar.altaf on 9/15/18.
 */
@RestController
@PropertySource(value = {"classpath:application.yml"})
public class SampleRestController {
    @Value("${app.filedir}")
    private String appFileDir;
    @Value("${app.filename}")
    private String appFileName;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/filterByAge")
    public JSONResponse filterByAge(@RequestBody SearchCriteria searchCriteria) throws IOException {
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setEmployees(performFilter(searchCriteria));

        return jsonResponse;
    }

    @RequestMapping(value = "/delete/{id}")
    public void delete(@PathVariable Long id, HttpServletResponse response) throws IOException {
        try {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setId(id);
            List<Employee> employees = performFilter(searchCriteria);

            employeeService.saveEmployees(employees);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update")
    public void update(@RequestBody Employee employee, HttpServletResponse response) throws IOException {
        try {
            SearchCriteria searchCriteria = null;
//            searchCriteria.setId(employee.getId());
            List<Employee> employees = employeeService.getAllEmployees();

            for (Employee employee1 : employees) {
                if (employee1.getId().equals(employee.getId())) {
                    BeanUtils.copyProperties(employee, employee1);
                }
            }

            employeeService.saveEmployees(employees);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/create")
    public JSONResponse create(@RequestBody Employee employee, HttpServletResponse response) throws IOException {
        JSONResponse jsonResponse = new JSONResponse();
        try {
            List<Employee> employees = employeeService.getAllEmployees();

            for (Employee employee1 : employees) {
                if (employee1.getId().equals(employee.getId())) {
                    throw new Exception("Employee with Id : '" + employee.getId() + "' already exists");
                }
            }
            employees.add(employee);

            employeeService.saveEmployees(employees);

            jsonResponse.setMessage("Successfully created new employee.");
        } catch (Exception ex) {
            jsonResponse.setMessage(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return jsonResponse;
    }

    private List<Employee> performFilter(SearchCriteria searchCriteria) throws IOException {
        List<Employee> allEmployees = employeeService.getAllEmployees();

        List<Employee> filteredEmployees = new ArrayList<>();

        if (null == searchCriteria) {
            return allEmployees;
        }

        for (Employee employee : allEmployees) {
            if (null != searchCriteria.getId()) {
                if (!employee.getId().equals(searchCriteria.getId())) {
                    filteredEmployees.add(employee);
                }
            }

            if (Operator.EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() == searchCriteria.getValue()) {
                filteredEmployees.add(employee);
            } else if (Operator.NOT_EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() != searchCriteria.getValue()) {
                filteredEmployees.add(employee);
            } else if (Operator.GREATER_EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() >= searchCriteria.getValue()) {
                filteredEmployees.add(employee);
            } else if (Operator.LESS_THAN_EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() <= searchCriteria.getValue()) {
                filteredEmployees.add(employee);
            } else if (Operator.LESS_THAN.getCode().equals(searchCriteria.getOperator()) && employee.getAge() < searchCriteria.getValue()) {
                filteredEmployees.add(employee);
            } else if (Operator.GREATER_THAN.getCode().equals(searchCriteria.getOperator()) && employee.getAge() > searchCriteria.getValue()) {
                filteredEmployees.add(employee);
            }
        }

        if (null != searchCriteria.getSort()) {

            Collections.sort(filteredEmployees, new Comparator<Employee>() {
                @Override
                public int compare(Employee t, Employee t1) {
                    if (Order.ASC.getCode().equalsIgnoreCase(searchCriteria.getSort())) {
                        return t.getAge().compareTo(t1.getAge());
                    } else if (Order.DESC.getCode().equalsIgnoreCase(searchCriteria.getSort())) {
                        return t1.getAge().compareTo(t.getAge());
                    }
                    return 0;
                }
            });
        }
        return filteredEmployees;

    }
}