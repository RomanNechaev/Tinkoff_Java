package tinkoff.training.utils.exceptions.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class ClientApiException extends RuntimeException {
    private final HttpStatus targetStatus;
    private final String errorMessage;

}
