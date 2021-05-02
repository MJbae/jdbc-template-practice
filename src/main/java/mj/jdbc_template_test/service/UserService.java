package mj.jdbc_template_test.service;

import mj.jdbc_template_test.domain.user.UserRepository;
import mj.jdbc_template_test.util.UriUtil;
import mj.jdbc_template_test.web.dto.TokenDto;
import mj.jdbc_template_test.web.dto.UserResponseDto;
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

    public final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final UriUtil uriUtil;


    public UserService(UserRepository userRepository, RestTemplateBuilder restTemplateBuilder, UriUtil uriUtil) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplateBuilder.build();
        this.uriUtil = uriUtil;
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

        return restTemplate.exchange(uriUtil.getAccessTokenUri(tempCode), HttpMethod.POST, httpEntity, TokenDto.class).getBody();
    }
}
