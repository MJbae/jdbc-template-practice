package mj.jdbc_template_test;

import mj.jdbc_template_test.model.Employee;
import mj.jdbc_template_test.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class EmployeeRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final static String FIRST_NAME_MJ = "M";
    private final static String LAST_NAME_MJ = "J";
    private final static long INCOME_MJ = 10000000;

    private final static String FIRST_NAME_T = "T";
    private final static String LAST_NAME_T = "T";
    private final static long INCOME_T = 30000;

    private Employee employeeMJ;
    private Employee employeeTT;

    @BeforeEach
    void setData(){
        employeeMJ = new Employee(FIRST_NAME_MJ, LAST_NAME_MJ, INCOME_MJ);
        employeeTT = new Employee(FIRST_NAME_T, LAST_NAME_T, INCOME_T);
    }

    @AfterEach
    void cleanup() {
        jdbcTemplate.update("delete from employees where id > 3");
    }

    @Test
    void saveTest() {
        employeeRepository.save(employeeMJ);

        List<Employee> employees = employeeRepository.findAll();
        Employee employee = employees.get(employees.size() - 1);

        assertThat(employees).isNotNull();
        assertThat(employee.getId()).isEqualTo(employees.size());
        assertThat(employee.getFirstName()).isEqualTo(FIRST_NAME_MJ);
        assertThat(employee.getLastName()).isEqualTo(LAST_NAME_MJ);
        assertThat(employee.getYearlyIncome()).isEqualTo(INCOME_MJ);
    }

    @Test
    void updateTest() {
        Long savedMJId = employeeRepository.simpleSave(employeeMJ);

        employeeTT.setId(savedMJId);
        employeeRepository.update(employeeTT);

        List<Employee> employees = employeeRepository.findAll();
        Employee employee = employees.get(employees.size() - 1);

        assertThat(employees).isNotNull();
        assertThat(employee.getId()).isEqualTo(savedMJId);
        assertThat(employee.getFirstName()).isEqualTo(FIRST_NAME_T);
        assertThat(employee.getLastName()).isEqualTo(LAST_NAME_T);
        assertThat(employee.getYearlyIncome()).isEqualTo(INCOME_T);
    }

    @Test
    void simpleSaveTest() {
        Long savedTTId = employeeRepository.simpleSave(employeeTT);

        List<Employee> employees = employeeRepository.findAll();
        Employee employee = employees.get(employees.size() - 1);

        assertThat(employees).isNotNull();
        assertThat(employee.getId()).isEqualTo(savedTTId);
        assertThat(employee.getFirstName()).isEqualTo(FIRST_NAME_T);
        assertThat(employee.getLastName()).isEqualTo(LAST_NAME_T);
        assertThat(employee.getYearlyIncome()).isEqualTo(INCOME_T);
    }

    @Test
    void deleteEmployee(){
        Long id = 3L;
        int sizeBefore = employeeRepository.findAll().size();

        assertThat(employeeRepository.delete(id)).isTrue();

        int sizeAfter = employeeRepository.findAll().size();
        assertThat(sizeAfter).isEqualTo(sizeBefore - 1);
     }


}
