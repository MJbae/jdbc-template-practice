package mj.jdbc_template_test.oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.util.OauthUtil;
import mj.jdbc_template_test.util.UriUtil;
import mj.jdbc_template_test.web.UserController;
import mj.jdbc_template_test.web.dto.GitHubUser;
import mj.jdbc_template_test.web.dto.Jwt;
import mj.jdbc_template_test.web.dto.TokenDto;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UriUtil uriUtil;
    private final OauthUtil oauthUtil;
    private final RestTemplate restTemplate;

    public LoginController(UriUtil uriUtil, OauthUtil oauthUtil, RestTemplateBuilder restTemplateBuilder) {
        this.uriUtil = uriUtil;
        this.oauthUtil = oauthUtil;
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping
    public void login(HttpServletResponse response) throws IOException {
        logger.info("login");

        response.sendRedirect(uriUtil.getTempCodeUri());
    }

    @GetMapping("/callback")
    public ResponseEntity<Jwt> callbackByOauth(@RequestParam("code") String tempCode) {

        TokenDto accessToken = getTokenByTempCode(tempCode);
        GitHubUser gitHubUser = getUserInfo(accessToken.getAccess_token());

        String jwt = getJwt(gitHubUser);

        return ResponseEntity.ok(new Jwt(jwt));
    }

    private String getJwt(GitHubUser gitHubUser) {
        Algorithm algorithm = Algorithm.HMAC256(oauthUtil.getJwtSecret());

        return JWT.create()
                .withClaim("login", gitHubUser.getLogin())
                .withClaim("name", gitHubUser.getName())
                .withIssuer(oauthUtil.getJwtIssuer())
                .withExpiresAt(toDate(LocalDateTime.now().plusSeconds(oauthUtil.getJwtExpireSecs())))
                .sign(algorithm);
    }

    private TokenDto getTokenByTempCode(String tempCode) {
        //TODO: null 처리 어떻게?
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(uriUtil.getAccessTokenUri(tempCode), HttpMethod.POST, httpEntity, TokenDto.class).getBody();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private GitHubUser getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(uriUtil.getUserInfoUri(), HttpMethod.GET, httpEntity, GitHubUser.class).getBody();
    }
}
