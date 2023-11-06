package tinkoff.training.services.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.Role;
import tinkoff.training.entities.User;
import tinkoff.training.models.AuthenticationRequest;
import tinkoff.training.repositories.UserRepository;
import tinkoff.training.services.auth.AuthenticationService;
import tinkoff.training.utils.exceptions.application.EntityExistsException;
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public void register(AuthenticationRequest request) {
        userRepository
                .findUserByUsername(request.getName())
                .ifPresent(exception -> {
                    throw new EntityExistsException("Username already used! Input another name");
                });
        User user = User.builder()
                .username(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
}
