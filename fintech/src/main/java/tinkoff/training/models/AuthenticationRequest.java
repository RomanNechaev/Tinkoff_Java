package tinkoff.training.models;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @Size(max = 20, message = "Name length must be at most 20 characters")
    private String name;
    private String password;
}
