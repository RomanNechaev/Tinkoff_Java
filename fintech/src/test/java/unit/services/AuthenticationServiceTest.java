package unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import tinkoff.training.entities.Role;
import tinkoff.training.entities.User;
import tinkoff.training.models.AuthenticationRequest;
import tinkoff.training.repositories.UserRepository;
import tinkoff.training.services.auth.AuthenticationService;
import tinkoff.training.services.auth.impl.AuthenticationServiceImpl;
import tinkoff.training.utils.exceptions.application.EntityExistsException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = AuthenticationServiceImpl.class)
@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    UserRepository userRepository;
    private AuthenticationService authenticationService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private final AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationServiceImpl(passwordEncoder, userRepository);
        authenticationRequest.setName("test");
        authenticationRequest.setName("password");
    }

    @Test
    void canCreateUserIfUserDontExist() {
        given(userRepository.findUserByUsername(authenticationRequest.getName())).willReturn(Optional.empty());

        authenticationService.register(authenticationRequest);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getUsername()).isEqualTo(authenticationRequest.getName());
    }

    @Test
    void eachUserMustHaveUserRole() {
        given(userRepository.findUserByUsername(authenticationRequest.getName())).willReturn(Optional.empty());

        authenticationService.register(authenticationRequest);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        assertThat(userArgumentCaptor.getValue().getRole()).isEqualTo(Role.USER);
    }

    @Test
    void canCreateUserIfUserExistsShouldThrowEntityExistException() {
        User user = User.builder()
                .username("test")
                .password("test")
                .role(Role.USER)
                .id(1L)
                .build();

        given(userRepository.findUserByUsername(authenticationRequest.getName())).willReturn(Optional.of(user));
        assertThatThrownBy(() -> authenticationService.register(authenticationRequest)).isInstanceOf(EntityExistsException.class);

        verify(userRepository, never()).save(user);
    }
}
