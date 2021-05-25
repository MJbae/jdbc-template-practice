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
@RequestMapping("/api/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> viewAllUsers() {
        logger.info("모든 사용자 정보 요청");

        return userService.findAllUser();
    }
}
