package tinkoff.training.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityExistsException extends RuntimeException {
    public EntityExistsException(String errorMessage) {
        super(errorMessage);
    }

}
