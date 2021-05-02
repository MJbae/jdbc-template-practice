package mj.jdbc_template_test.service;

import mj.jdbc_template_test.domain.user.UserRepository;
import mj.jdbc_template_test.web.dto.TokenDto;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:/oauth.properties")
public class UserService {

    public final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private Environment environment;

    private static final String LOGIN_URI = "github.authorize.url";
    private static final String REDIRECT_URI = "github.callback.url";
    private static final String TOKEN_URI = "github.access.token.url";
    public static final String CLIENT_ID = "github.client.id";
    public static final String CLIENT_SECRET = "github.secret";
    public static final String SCOPE = "github.scope";
    private String clientId;
    private String redirectUri;

    public UserService(UserRepository userRepository, RestTemplateBuilder restTemplateBuilder, Environment environment) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplateBuilder.build();
        this.environment = environment;
    }

    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user))
                .collect(Collectors.toList());
    }

    public TokenDto getTokenByTempCode(String tempCode) {
        //TODO: null 처리 어떻게?
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(getAccessTokenUri(tempCode), HttpMethod.POST, httpEntity, TokenDto.class).getBody();
    }

    public String getTempCodeUri() {
        clientId = environment.getProperty(CLIENT_ID);
        redirectUri = environment.getProperty(REDIRECT_URI);

        String loginUri = environment.getProperty(LOGIN_URI);
        String scope = environment.getProperty(SCOPE);

        return loginUri
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=" + scope;
    }

    public String getAccessTokenUri(String tempCode) {
        String accessTokenUri = environment.getProperty(TOKEN_URI);
        String clientSecret = environment.getProperty(CLIENT_SECRET);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(accessTokenUri)
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", tempCode);

        return accessTokenUri
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&code=" + tempCode;
    }

}
