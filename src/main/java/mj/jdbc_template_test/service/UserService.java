package mj.jdbc_template_test.service;

import mj.jdbc_template_test.domain.user.User;
import mj.jdbc_template_test.domain.user.UserRepository;
import mj.jdbc_template_test.util.UriUtil;
import mj.jdbc_template_test.web.UserController;
import mj.jdbc_template_test.web.dto.GitHubUser;
import mj.jdbc_template_test.web.dto.TokenDto;
import mj.jdbc_template_test.web.dto.GithubUserInfoDto;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final UriUtil uriUtil;


    public UserService(UserRepository userRepository, RestTemplateBuilder restTemplateBuilder, UriUtil uriUtil) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplateBuilder.build();
        this.uriUtil = uriUtil;
    }

//    public UserResponseDto login(GithubUserInfoDto githubUserInfoDto) {
//        User user = userRepository.findByGithubId(githubUserInfoDto.getId());
//
//        user.update(githubUserInfoDto);
//
//        return new UserResponseDto(user);
//    }

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


        logger.info("temp uri : {}", uriUtil.getAccessTokenUri(tempCode));
        return restTemplate.exchange(uriUtil.getAccessTokenUri(tempCode), HttpMethod.POST, httpEntity, TokenDto.class).getBody();
    }

    public GitHubUser getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "token " + accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);

        return restTemplate.exchange(uriUtil.getUserInfoUri(), HttpMethod.GET, httpEntity, GitHubUser.class).getBody();
    }
}
