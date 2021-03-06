package mj.jdbc_template_test.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<User> findAll() {
        String sqlQuery = "select id, first_name, last_name, yearly_income from employees";
        return jdbcTemplate.query(sqlQuery, memberRowMapper());
    }

    public User findById(long id) {
        String sqlQuery = "select id, first_name, last_name, yearly_income from employees where id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, memberRowMapper(), id);
    }

    public void save(User user) {
        String sqlQuery = "insert into employees(first_name, last_name, yearly_income) " +
                "values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getFirstName(),
                user.getLastName(),
                user.getYearlyIncome());
    }

    public long simpleSave(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("employees")
                .usingGeneratedKeyColumns("id");
        return simpleJdbcInsert.executeAndReturnKey(user.toMap()).longValue();
    }

    public void update(User user) {
        String sqlQuery = "update employees set " +
                "first_name = ?, last_name = ?, yearly_income = ? " +
                "where id = ?";

        jdbcTemplate.update(sqlQuery
                , user.getFirstName()
                , user.getLastName()
                , user.getYearlyIncome()
                , user.getId());
    }

    public boolean delete(long id) {
        String sqlQuery = "delete from employees where id = ?";
        return jdbcTemplate.update(sqlQuery, id) > 0;
    }

    private RowMapper<User> memberRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setYearlyIncome(rs.getLong("yearly_income"));
            return user;
        };
    }

}
