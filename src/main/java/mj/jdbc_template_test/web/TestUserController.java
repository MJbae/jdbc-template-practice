package mj.jdbc_template_test.web;

import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.dto.GitHubUser;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/test_users")
public class TestUserController {

    private final Logger logger = LoggerFactory.getLogger(TestUserController.class);
    private final UserService userService;

    public TestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> viewAllUsers() {

        return userService.findAllUser();
    }

    @GetMapping("/{userId}")
    public UserResponseDto viewUserByUserId(@PathVariable Long userId) {
        logger.debug("userId: {}", userId);
        return userService.findOneByUserId(userId);
    }
}
