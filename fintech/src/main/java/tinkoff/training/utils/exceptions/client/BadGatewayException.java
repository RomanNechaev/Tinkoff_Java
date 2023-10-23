package tinkoff.training.utils.exceptions.client;

import org.springframework.http.HttpStatus;
import tinkoff.training.utils.exceptions.application.ApplicationException;

public class BadGatewayException extends ApplicationException {
    public BadGatewayException(String errorMessage) {
        super(HttpStatus.BAD_GATEWAY, errorMessage);
    }
}
