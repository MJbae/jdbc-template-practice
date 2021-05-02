package mj.jdbc_template_test;

import mj.jdbc_template_test.domain.user.User;
import mj.jdbc_template_test.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    private final static String FIRST_NAME_MJ = "M";
    private final static String LAST_NAME_MJ = "J";
    private final static long INCOME_MJ = 10000000;

    private final static String FIRST_NAME_T = "T";
    private final static String LAST_NAME_T = "T";
    private final static long INCOME_T = 30000;

    private User userMJ;
    private User userTT;

    @BeforeEach
    void setData(){
        userMJ = new User(FIRST_NAME_MJ, LAST_NAME_MJ, null, INCOME_MJ);
        userTT = new User(FIRST_NAME_T, LAST_NAME_T, null, INCOME_T);
    }

    @AfterEach
    void cleanup() {
        jdbcTemplate.update("delete from employees where id > 3");
    }

    @Test
    void saveTest() {
        userRepository.save(userMJ);

        List<User> users = userRepository.findAll();
        User user = users.get(users.size() - 1);

        assertThat(users).isNotNull();
        assertThat(user.getId()).isEqualTo(users.size());
        assertThat(user.getFirstName()).isEqualTo(FIRST_NAME_MJ);
        assertThat(user.getLastName()).isEqualTo(LAST_NAME_MJ);
        assertThat(user.getYearlyIncome()).isEqualTo(INCOME_MJ);
    }

    @Test
    void updateTest() {
        Long savedMJId = userRepository.simpleSave(userMJ);

        userTT.setId(savedMJId);
        userRepository.update(userTT);

        List<User> users = userRepository.findAll();
        User user = users.get(users.size() - 1);

        assertThat(users).isNotNull();
        assertThat(user.getId()).isEqualTo(savedMJId);
        assertThat(user.getFirstName()).isEqualTo(FIRST_NAME_T);
        assertThat(user.getLastName()).isEqualTo(LAST_NAME_T);
        assertThat(user.getYearlyIncome()).isEqualTo(INCOME_T);
    }

    @Test
    void simpleSaveTest() {
        Long savedTTId = userRepository.simpleSave(userTT);

        List<User> users = userRepository.findAll();
        User user = users.get(users.size() - 1);

        assertThat(users).isNotNull();
        assertThat(user.getId()).isEqualTo(savedTTId);
        assertThat(user.getFirstName()).isEqualTo(FIRST_NAME_T);
        assertThat(user.getLastName()).isEqualTo(LAST_NAME_T);
        assertThat(user.getYearlyIncome()).isEqualTo(INCOME_T);
    }

    @Test
    void deleteEmployee(){
        Long id = 3L;
        int sizeBefore = userRepository.findAll().size();

        assertThat(userRepository.delete(id)).isTrue();

        int sizeAfter = userRepository.findAll().size();
        assertThat(sizeAfter).isEqualTo(sizeBefore - 1);
     }


}
