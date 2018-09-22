package raydar.research.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import raydar.research.model.Employee;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by azhar.altaf on 9/22/18.
 */
@Service
@PropertySource(value = {"classpath:application.yml"})
public class EmployeeServiceImpl implements EmployeeService {
    @Value("${app.filedir}")
    private String appFileDir;
    @Value("${app.filename}")
    private String appFileName;

    @Override
    public List<Employee> getAllEmployees() throws IOException {
        return populateEmployee();

//        List<Employee> filteredEmployees = new ArrayList<>();
//
//        if (null == searchCriteria) {
//            return allEmployees;
//        }
//
//        for (Employee employee : allEmployees) {
//            if (null != searchCriteria.getId()) {
//                if (!employee.getId().equals(searchCriteria.getId())) {
//                    filteredEmployees.add(employee);
//                }
//            }
//
//            if (Operator.EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() == searchCriteria.getValue()) {
//                filteredEmployees.add(employee);
//            } else if (Operator.NOT_EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() != searchCriteria.getValue()) {
//                filteredEmployees.add(employee);
//            } else if (Operator.GREATER_EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() >= searchCriteria.getValue()) {
//                filteredEmployees.add(employee);
//            } else if (Operator.LESS_THAN_EQUAL.getCode().equals(searchCriteria.getOperator()) && employee.getAge() <= searchCriteria.getValue()) {
//                filteredEmployees.add(employee);
//            } else if (Operator.LESS_THAN.getCode().equals(searchCriteria.getOperator()) && employee.getAge() < searchCriteria.getValue()) {
//                filteredEmployees.add(employee);
//            } else if (Operator.GREATER_THAN.getCode().equals(searchCriteria.getOperator()) && employee.getAge() > searchCriteria.getValue()) {
//                filteredEmployees.add(employee);
//            }
//        }
//
//        if (null != searchCriteria.getSort()) {
//
//            Collections.sort(filteredEmployees, new Comparator<Employee>() {
//                @Override
//                public int compare(Employee t, Employee t1) {
//                    if (Order.ASC.getCode().equalsIgnoreCase(searchCriteria.getSort())) {
//                        return t.getAge().compareTo(t1.getAge());
//                    } else if (Order.DESC.getCode().equalsIgnoreCase(searchCriteria.getSort())) {
//                        return t1.getAge().compareTo(t.getAge());
//                    }
//                    return 0;
//                }
//            });
//        }
//        return filteredEmployees;
    }

    private List<Employee> populateEmployee() throws IOException {
        List<Employee> employees = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Employee>> typeReference = new TypeReference<List<Employee>>() {
        };
        return objectMapper.readValue(new File(appFileDir + appFileName), typeReference);
    }

    @Override
    public void saveEmployees(List<Employee> employees) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(appFileDir + appFileName), employees);
    }
}
