package mj.jdbc_template_test.web;

import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.dto.TokenDto;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponseDto> viewAllUsers() {
        logger.info("모든 사용자 정보 요청");

        return userService.findAllUser();
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        logger.info("login");

        response.sendRedirect(userService.getTempCodeUri());
    }

    @GetMapping("/login/callback")
    public TokenDto callbackByOauth(@RequestParam("code") String tempCode) {
        logger.info("callback code: {}", tempCode);

        return userService.getTokenByTempCode(tempCode);
    }
}
