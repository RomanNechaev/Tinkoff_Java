package tinkoff.training.utils.exceptions.client;

import org.springframework.http.HttpStatus;

public class NotSupportedEncodingException extends ClientApiException {
    public NotSupportedEncodingException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
