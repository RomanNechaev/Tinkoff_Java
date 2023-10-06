package tinkoff.training.utils.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException(String errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }
}
