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

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user))
                .collect(Collectors.toList());
    }

    public UserResponseDto findOneByUserId(Long userId) {
        return new UserResponseDto(userRepository.findByGithubId(userId));
    }
}
