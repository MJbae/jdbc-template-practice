package mj.jdbc_template_test.service;

import mj.jdbc_template_test.domain.user.UserRepository;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> findAllUser() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user))
                .collect(Collectors.toList());
    }

}
