package mj.jdbc_template_test;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mj.jdbc_template_test.domain.user.User;
import mj.jdbc_template_test.service.GreetingService;
import mj.jdbc_template_test.web.GreetingController;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

//@WebMvcTest(TestUserController.class)
@WebMvcTest(GreetingController.class)
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

//    @MockBean
//    private UserService userservice;

    @MockBean
    private GreetingService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(service.greet()).thenReturn("Hello, Mock");
        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Mock")));
    }


//    @Test
//    public void 유저아이디_조건에_따라_특정_사용자_조회() throws Exception {
//
//        when(service.findOneByUserId(GITHUB_ID_MJ)).thenReturn(
//                new UserResponseDto(new User(FIRST_NAME_MJ, LAST_NAME_MJ, GITHUB_ID_MJ, INCOME_MJ))
//        );
//
//        this.mockMvc.perform(get("/api/test_users/"+GITHUB_ID_MJ))
//                .andDo(print());
//
//    }
}
