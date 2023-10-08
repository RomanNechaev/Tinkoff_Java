package tinkoff.training.utils.exceptions.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ApplicationException extends RuntimeException {
    private final HttpStatus targetStatus;
    private final String errorMessage;

    protected ApplicationException(HttpStatus targetStatus, String errorMessage) {
        this.targetStatus = targetStatus;
        this.errorMessage = errorMessage;
    }
}
