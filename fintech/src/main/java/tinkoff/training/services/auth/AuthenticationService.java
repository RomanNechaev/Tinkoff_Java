package tinkoff.training.services.auth;

import tinkoff.training.models.AuthenticationRequest;

public interface AuthenticationService {
    void register(AuthenticationRequest request);
}
