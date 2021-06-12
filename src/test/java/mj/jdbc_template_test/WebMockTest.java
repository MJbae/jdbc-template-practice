package mj.jdbc_template_test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mj.jdbc_template_test.domain.user.User;
import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.TestUserController;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TestUserController.class)
public class WebMockTest {
    private final static String FIRST_NAME_MJ = "M";
    private final static String LAST_NAME_MJ = "J";
    private final static long INCOME_MJ = 10000000;
    private final static Long GITHUB_ID_MJ = 123L;

    private final static String FIRST_NAME_T = "T";
    private final static String LAST_NAME_T = "T";
    private final static long INCOME_T = 123456234;
    private final static Long GITHUB_ID_T = 9123L;

    private User userMJ;
    private User userTT;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void 유저아이디_조건에_따라_특정_사용자_조회() throws Exception {
        String expectByFirstName = "$.[?(@.firstName == '%s')]";
        String expectByLastName = "$.[?(@.lastName == '%s')]";
        String expectByIncome = "$.[?(@.yearlyIncome == '%s')]";

        when(userService.findOneByUserId(GITHUB_ID_MJ)).thenReturn(
                new UserResponseDto(new User(FIRST_NAME_MJ, LAST_NAME_MJ, GITHUB_ID_MJ, INCOME_MJ))
        );

        this.mockMvc.perform(get("/api/test_users/"+GITHUB_ID_MJ))
                .andExpect(status().isOk())
                .andExpect(jsonPath(expectByFirstName, "M").exists())
                .andExpect(jsonPath(expectByLastName, "J").exists())
                .andExpect(jsonPath(expectByIncome, 10000000).exists());

    }
}
