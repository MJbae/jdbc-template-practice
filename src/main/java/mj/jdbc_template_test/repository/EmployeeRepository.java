package mj.jdbc_template_test.repository;

import mj.jdbc_template_test.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Employee> findAll() {
        String sqlQuery = "select id, first_name, last_name, yearly_income from employees";
        return jdbcTemplate.query(sqlQuery, memberRowMapper());
    }

    public void save(Employee employee) {
        String sqlQuery = "insert into employees(first_name, last_name, yearly_income) " +
                "values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getYearlyIncome());
    }

    public long simpleSave(Employee employee) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(employee.toMap()).longValue();
    }

    public void update(Employee employee) {
        String sqlQuery = "update employees set " +
                "first_name = ?, last_name = ?, yearly_income = ? " +
                "where id = ?";

        jdbcTemplate.update(sqlQuery
                , employee.getFirstName()
                , employee.getLastName()
                , employee.getYearlyIncome()
                , employee.getId());
    }

    public boolean delete(long id) {
        String sqlQuery = "delete from employees where id = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private RowMapper<Employee> memberRowMapper() {
        return (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setYearlyIncome(rs.getLong("yearly_income"));
            return employee;
        };
    }

}
