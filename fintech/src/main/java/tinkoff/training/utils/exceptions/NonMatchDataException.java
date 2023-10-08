package tinkoff.training.utils.exceptions;

import org.springframework.http.HttpStatus;

public class NonMatchDataException extends ApplicationException {
    public NonMatchDataException(String errorMessage) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, errorMessage);
    }
}
