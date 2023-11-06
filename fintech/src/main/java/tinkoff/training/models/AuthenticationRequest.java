package tinkoff.training.models;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String name;
    private String password;
}
