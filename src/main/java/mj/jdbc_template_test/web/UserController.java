package mj.jdbc_template_test.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.util.UriUtil;
import mj.jdbc_template_test.web.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Jwt> callbackByOauth(@RequestParam("code") String tempCode) {
        logger.info("callback code: {}", tempCode);

        TokenDto accessToken = userService.getTokenByTempCode(tempCode);
        GitHubUser gitHubUser = userService.getUserInfo(accessToken.getAccess_token());

        logger.info("gitHubUser: {}", gitHubUser);

        String jwt = getJwt(gitHubUser);

        return ResponseEntity.ok(new Jwt(jwt));
//        return userService.login(gitHubUser);
    }

    private String getJwt(GitHubUser gitHubUser) {
        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withClaim("login", gitHubUser.getLogin())
                .withClaim("name", gitHubUser.getName())
                // TODO: 추후 properties에서 관리
                .withIssuer("baseball-game")
                .sign(algorithm);
    }
}
