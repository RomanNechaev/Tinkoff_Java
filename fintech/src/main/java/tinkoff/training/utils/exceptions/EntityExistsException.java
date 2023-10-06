package tinkoff.training.utils.exceptions;

import org.springframework.http.HttpStatus;

public class EntityExistsException extends ApplicationException {
    public EntityExistsException(String errorMessage) {
        super(HttpStatus.CONFLICT, errorMessage);
    }

}
