package tinkoff.training.utils.exceptions.application;

import org.springframework.http.HttpStatus;

public class InternalApplicationError extends ApplicationException {
    public InternalApplicationError(String errorMessage) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Something wrong... " + errorMessage);
    }
}
