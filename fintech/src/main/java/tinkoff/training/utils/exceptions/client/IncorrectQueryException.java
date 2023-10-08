package tinkoff.training.utils.exceptions.client;

import org.springframework.http.HttpStatus;

public class IncorrectQueryException extends ClientApiException {
    public IncorrectQueryException(String message) {
        super(HttpStatus.BAD_REQUEST,message);
    }
}
