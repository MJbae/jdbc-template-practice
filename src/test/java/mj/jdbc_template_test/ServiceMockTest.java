package mj.jdbc_template_test;

import mj.jdbc_template_test.domain.user.UserRepository;
import mj.jdbc_template_test.service.UserService;
import mj.jdbc_template_test.web.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static mj.jdbc_template_test.TestData.userMJ;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceMockTest {

    @Mock
    UserService service;

    @Mock
    UserRepository repository;

    @Test
    void findOneByUserId(){
        service = new UserService(repository);

        when(repository.findByGithubId(1L)).thenReturn(userMJ);

        assertThat(service.findOneByUserId(1L))
                .isEqualTo(new UserResponseDto(userMJ));
    }
}
