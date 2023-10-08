package tinkoff.training.utils.exceptions.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ClientApiException extends RuntimeException {
    private final HttpStatus targetStatus;
    private final String errorMessage;

    protected ClientApiException(HttpStatus targetStatus, String errorMessage) {
        this.targetStatus = targetStatus;
        this.errorMessage = errorMessage;
    }
}
