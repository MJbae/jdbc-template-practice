package mj.jdbc_template_test.web;

import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.util.UriUtil;
import mj.jdbc_template_test.web.dto.TokenDto;
import mj.jdbc_template_test.web.dto.GithubUserInfoDto;
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
    private final UriUtil uriUtil;

    public UserController(UserService userService, UriUtil uriUtil) {
        this.userService = userService;
        this.uriUtil = uriUtil;
    }

    @GetMapping("/users")
    public List<UserResponseDto> viewAllUsers() {
        logger.info("모든 사용자 정보 요청");

        return userService.findAllUser();
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        logger.info("login");

        response.sendRedirect(uriUtil.getTempCodeUri());
    }

    @GetMapping("/login/callback")
    public UserResponseDto callbackByOauth(@RequestParam("code") String tempCode) {
        logger.info("callback code: {}", tempCode);

        TokenDto accessToken = userService.getTokenByTempCode(tempCode);
        GithubUserInfoDto githubUserInfoDto = userService.getUserInfo(accessToken.getAccess_token());

        return userService.login(githubUserInfoDto);
    }
}
