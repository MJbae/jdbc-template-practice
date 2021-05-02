package mj.jdbc_template_test.web;

import mj.jdbc_template_test.domain.user.User;
import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.dto.TokenDto;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@PropertySource("classpath:/oauth.properties")
@RestController
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Environment environment;
    private final UserService userService;
    private final RestTemplate restTemplate;

    private static final String LOGIN_URI = "github.authorize.url";
    private static final String REDIRECT_URI = "github.callback.url";
    private static final String TOKEN_URI = "github.access.token.url";
    public static final String CLIENT_ID = "github.client.id";
    public static final String CLIENT_SECRET = "github.secret";
    public static final String SCOPE = "github.scope";

    private String clientId;
    private String redirectUri;

    public UserController(UserService userService, Environment environment, RestTemplateBuilder restTemplateBuilder) {
        this.userService = userService;
        this.environment = environment;
        this.restTemplate = restTemplateBuilder.build();
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
    public TokenDto callbackByOauth(@RequestParam("code") String tempCode) {
        logger.info("callback code: {}", tempCode);

        return getTokenByTempCode(tempCode);
    }

    private TokenDto getTokenByTempCode(String tempCode) {
        //TODO: null 처리 어떻게?
        String accessTokenUri = environment.getProperty(TOKEN_URI);
        String clientSecret = environment.getProperty(CLIENT_SECRET);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(accessTokenUri)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", tempCode);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, httpEntity, TokenDto.class).getBody();
    }

}
