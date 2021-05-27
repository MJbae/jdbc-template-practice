package mj.jdbc_template_test.oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import mj.jdbc_template_test.util.OauthUtil;
import mj.jdbc_template_test.util.UriUtil;
import mj.jdbc_template_test.web.UserController;
import mj.jdbc_template_test.web.dto.GitHubUser;
import mj.jdbc_template_test.web.dto.Jwt;
import mj.jdbc_template_test.web.dto.TokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UriUtil uriUtil;
    private final OauthUtil oauthUtil;

    public LoginController(UriUtil uriUtil, OauthUtil oauthUtil) {
        this.uriUtil = uriUtil;
        this.oauthUtil = oauthUtil;
    }

    @GetMapping("/auth")
    public ResponseEntity<Jwt> callbackByOauth(String code) {
        logger.info("code: {}", code);

        RestTemplate gitHubRequest = new RestTemplate();

        GithubAccessTokenResponse accessToken = getTokenByTempCode(code, gitHubRequest)
                .orElseThrow(() -> new RuntimeException("no access Token"));

        GitHubUser gitHubUser = getUserInfo(accessToken, gitHubRequest)
                .orElseThrow(() -> new RuntimeException("no Github User"));

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

    private Optional<GithubAccessTokenResponse> getTokenByTempCode(String code, RestTemplate gitHubRequest) {
        RequestEntity<GithubAccessTokenRequest> request = RequestEntity
                .post(uriUtil.getTokenUri())
                .header("Accept", "application/json")
                .body(new GithubAccessTokenRequest(oauthUtil.getClientId(), oauthUtil.getSecretId(), code));

        ResponseEntity<GithubAccessTokenResponse> response = gitHubRequest
                .exchange(request, GithubAccessTokenResponse.class);

        return Optional.ofNullable(response.getBody());
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Optional<GitHubUser> getUserInfo(GithubAccessTokenResponse accessToken, RestTemplate gitHubRequest) {

        RequestEntity<Void> request = RequestEntity
                .get(uriUtil.getUserInfoUri())
                .header("Accept", "application/json")
                .header("Authorization", "token " + accessToken.getAccessToken())
                .build();

        ResponseEntity<GitHubUser> response = gitHubRequest
                .exchange(request, GitHubUser.class);

        return Optional.ofNullable(response.getBody());
    }
}
