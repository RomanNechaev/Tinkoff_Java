package tinkoff.training.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tinkoff.training.models.AuthenticationRequest;
import tinkoff.training.services.auth.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    public ResponseEntity<Void> register(@RequestBody AuthenticationRequest authenticationRequest) {
        authenticationService.register(authenticationRequest);
        return ResponseEntity.ok().build();
    }

}
