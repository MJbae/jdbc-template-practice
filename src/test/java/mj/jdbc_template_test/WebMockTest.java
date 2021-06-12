package mj.jdbc_template_test;

import static mj.jdbc_template_test.TestData.GITHUB_ID_MJ;
import static mj.jdbc_template_test.TestData.userMJ;
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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void 유저아이디_조건에_따라_특정_사용자_조회() throws Exception {
        String expectByFirstName = "$.[?(@.first_name == '%s')]";
        String expectByLastName = "$.[?(@.last_name == '%s')]";
        String expectByIncome = "$.[?(@.yearly_income == '%s')]";

        when(userService.findOneByUserId(GITHUB_ID_MJ)).thenReturn(
                new UserResponseDto(userMJ)
        );

        this.mockMvc.perform(get("/api/test_users/"+GITHUB_ID_MJ))
                .andExpect(status().isOk())
                .andExpect(jsonPath(expectByFirstName, "M").exists())
                .andExpect(jsonPath(expectByLastName, "J").exists())
                .andExpect(jsonPath(expectByIncome, 10000000).exists());

    }
}
