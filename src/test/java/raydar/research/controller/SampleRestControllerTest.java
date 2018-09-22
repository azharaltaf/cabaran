package raydar.research.controller;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import raydar.research.model.*;
import raydar.research.service.EmployeeService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.BDDMockito.given;

/**
 * Created by azhar.altaf on 9/22/18.
 */
@RunWith(MockitoJUnitRunner.class)
//@PropertySource(value={"classpath:application.yml"})
//@ContextConfiguration(initializers = {ConfigFileApplicationContextInitializer.class})
public class SampleRestControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private SampleRestController sampleRestController;

    @Mock
    private EmployeeService employeeService;

    private JacksonTester<Employee> jsonEmployee;

    @Before
    public void setUp() throws IOException {
        mockMvc = MockMvcBuilders.standaloneSetup(sampleRestController)
                .build();
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Test 1", 25, new BigDecimal("2500")));
        employees.add(new Employee(2L, "Test 2", 20, new BigDecimal("2500")));
        employees.add(new Employee(150L, "Test 150", 45, new BigDecimal("2500")));
        employees.add(new Employee(300L, "Test 300", 35, new BigDecimal("2500")));
        employees.add(new Employee(85L, "Test 85", 25, new BigDecimal("2500")));
        given(employeeService.getAllEmployees()).willReturn(employees);
//        Mockito.when(employeeService.filterEmployees(any(SearchCriteria.class))).thenReturn(null);
    }

    @Test
    public void testIfIdToDeleteIsInTheFilteredEmployees() {
        try {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setId(150L);
            JSONResponse jsonResponse = sampleRestController.filterByAge(searchCriteria);

            assertThat(jsonResponse.getEmployees().size()).isEqualTo(4);
            for (Employee employee : jsonResponse.getEmployees()) {
                if (employee.getId().equals(150L)) {
                    fail("Error, this id should not exist as it is filtered");
                }
            }
//            MockHttpServletResponse response = mockMvc.perform(
//                    get("/superheroes/2").accept(MediaType.APPLICATION_JSON))
//                    .andReturn().getResponse();

            // then
//            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//            assertThat(response.getContentAsString()).isEqualTo(
//                    jsonSuperHero.write(new SuperHero("Rob", "Mannon", "RobotMan")).getJson()
//            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortWithEqualOperator() {
        try {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setSort(Order.DESC.getCode());
            searchCriteria.setValue(25);
            searchCriteria.setOperator(Operator.EQUAL.getCode());
            JSONResponse jsonResponse = sampleRestController.filterByAge(searchCriteria);

            assertThat(jsonResponse.getEmployees().size()).isEqualTo(2);
            assertThat(jsonResponse.getEmployees().get(0).getAge()).isEqualTo(25);
            assertThat(jsonResponse.getEmployees().get(1).getAge()).isEqualTo(25);

            searchCriteria.setValue(30);
            jsonResponse = sampleRestController.filterByAge(searchCriteria);
            assertThat(jsonResponse.getEmployees().size()).isEqualTo(0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortWithNotEqualOperator() {
        try {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.setSort(Order.DESC.getCode());
            searchCriteria.setValue(25);
            searchCriteria.setOperator(Operator.NOT_EQUAL.getCode());
            JSONResponse jsonResponse = sampleRestController.filterByAge(searchCriteria);

            assertThat(jsonResponse.getEmployees().size()).isEqualTo(3);
            assertThat(jsonResponse.getEmployees().get(0).getAge()).isEqualTo(45);
            assertThat(jsonResponse.getEmployees().get(1).getAge()).isEqualTo(35);
            assertThat(jsonResponse.getEmployees().get(2).getAge()).isEqualTo(20);

            searchCriteria.setSort(Order.ASC.getCode());
            jsonResponse = sampleRestController.filterByAge(searchCriteria);
            assertThat(jsonResponse.getEmployees().get(0).getAge()).isEqualTo(20);
            assertThat(jsonResponse.getEmployees().get(1).getAge()).isEqualTo(35);
            assertThat(jsonResponse.getEmployees().get(2).getAge()).isEqualTo(45);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateEmployeeWithExistingId() {
        Employee newEmployee = new Employee(150L, "Testing 451", 45, new BigDecimal("2100"));
        try {
            // try creating employee with existing id
            Gson gson = new Gson();
            String json = gson.toJson(newEmployee);
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/create")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(json);
//            this.mockMvc.perform(builder)
//                    .andExpect(ok)
//                    .andDo(MockMvcResultHandlers.print());
            MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();
            assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);

            newEmployee.setId(1000L);
            json = gson.toJson(newEmployee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateEmployeeWithNonExistingId() {
        Employee newEmployee = new Employee(1000L, "Testing 1000", 45, new BigDecimal("2100"));
        try {
            // try creating employee with existing id
            Gson gson = new Gson();
            String json = gson.toJson(newEmployee);
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/create")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(json);
            MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();
            assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdate() {
        Employee newEmployee = new Employee(150L, "Testing 150 New", 45, new BigDecimal("2500"));
        try {
            Gson gson = new Gson();
            String json = gson.toJson(newEmployee);
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/update")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(json);
            MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();
            assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
