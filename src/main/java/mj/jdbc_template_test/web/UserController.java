package mj.jdbc_template_test.web;

import mj.jdbc_template_test.domain.user.User;
import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    public final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponseDto> viewAllUsers(){
        logger.info("모든 사용자 정보 요청");
        return userService.findAllUser();
    }

}
