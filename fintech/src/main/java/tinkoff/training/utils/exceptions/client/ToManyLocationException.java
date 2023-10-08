package tinkoff.training.utils.exceptions.client;

import org.springframework.http.HttpStatus;

public class ToManyLocationException extends ClientApiException {
    public ToManyLocationException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
