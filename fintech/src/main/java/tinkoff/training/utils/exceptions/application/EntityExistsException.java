package tinkoff.training.utils.exceptions.application;

import org.springframework.http.HttpStatus;

public class EntityExistsException extends ApplicationException {
    public EntityExistsException(String errorMessage) {
        super(HttpStatus.CONFLICT, errorMessage);
    }

}
