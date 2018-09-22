package raydar.research.model;

import java.math.BigDecimal;

/**
 * Created by azhar.altaf on 9/20/18.
 */
public class Employee {
    private Long id;
    private String fullName;
    private Integer age;
    private BigDecimal salary;

    public Employee() {
    }

    public Employee(Long id, String fullName, Integer age, BigDecimal salary) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
