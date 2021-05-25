package mj.jdbc_template_test.web;

import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> viewAllUsers(HttpServletRequest request) {
        GitHubUser user = (GitHubUser) request.getAttribute("user");
        logger.info("user login 확인: {}", user.getLogin());

        return userService.findAllUser();
    }
}
