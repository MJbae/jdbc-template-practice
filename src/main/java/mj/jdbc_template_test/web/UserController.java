package mj.jdbc_template_test.web;

import mj.jdbc_template_test.domain.user.User;
import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@PropertySource("classpath:/oauth.properties")
@RestController
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final Environment environment;

    private static final String LOGIN_URI = "github.authorize.url";
    private static final String REDIRECT_URI = "github.callback.url";
    private static final String TOKEN_URI = "github.access.token.url";
    public static final String CLIENT_ID = "github.client.id";
    public static final String CLIENT_SECRET = "github.secret";
    public static final String SCOPE = "github.scope";

    private String clientId;
    private String redirectUri;



    public final UserService userService;

    public UserController(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }

    @GetMapping("/users")
    public List<UserResponseDto> viewAllUsers() {
        logger.info("모든 사용자 정보 요청");
        return userService.findAllUser();
    }

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        logger.info("login");

        clientId = environment.getProperty(CLIENT_ID);
        redirectUri = environment.getProperty(REDIRECT_URI);

        String loginUri = environment.getProperty(LOGIN_URI);
        String scope = environment.getProperty(SCOPE);

        response.sendRedirect(loginUri
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=" + scope);
    }

    @GetMapping("/login/callback")
    public void callbackByOauth(@RequestParam("code") String tempCode) {
        logger.info("callback code: {}", tempCode);

//        TokenDTO tokenDTO = getTokenByTempCode(tempCode);


        String accessTokenUri = environment.getProperty(TOKEN_URI);
        String clientSecret = environment.getProperty(CLIENT_SECRET);


    }

}
