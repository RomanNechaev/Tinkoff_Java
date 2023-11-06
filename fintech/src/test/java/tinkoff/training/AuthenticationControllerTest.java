package tinkoff.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tinkoff.training.models.AuthenticationRequest;
import tinkoff.training.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    private AuthenticationRequest authenticationRequest;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setName("test");
        authenticationRequest.setPassword("test");
    }

    @Test
    void registrationWithoutAuthenticationAndDontExistUsernameShouldBeAccess() throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(authenticationRequest);
        assertThat(userRepository.existsByUsername(authenticationRequest.getName())).isFalse();
        var requestBuilder = post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
        assertThat(userRepository.existsByUsername(authenticationRequest.getName())).isTrue();
    }

    @Test
    void registrationWithoutAuthenticationAndExistUsernameShouldBeReturnEmptyResponseWithStatusConflict() throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(authenticationRequest);
        assertThat(userRepository.findAll()).hasSize(0);

        var requestBuilder = post("/api/auth/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);
        mockMvc.perform(requestBuilder);
        assertThat(userRepository.findAll()).hasSize(1);
        mockMvc.perform(requestBuilder)
                .andExpectAll(status().isConflict(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", Matchers.is("Username already used! Input another name")));

        assertThat(userRepository.findAll()).hasSize(1);
    }
}
