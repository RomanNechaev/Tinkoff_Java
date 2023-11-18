package tinkoff.training.utils.exceptions;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tinkoff.training.models.ErrorMessage;
import tinkoff.training.models.ValidationErrorResponse;
import tinkoff.training.models.Violation;
import tinkoff.training.utils.exceptions.application.ApplicationException;
import tinkoff.training.utils.exceptions.client.ClientApiException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Перехватывает и обрабатывает ошибоки, которые могут возникуть в приложении
 */
@RestControllerAdvice
public class ExceptionApiHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorMessage> handleApplicationException(ApplicationException e) {
        return ResponseEntity.status(e.getTargetStatus()).body(new ErrorMessage(e.getErrorMessage()));
    }

    @ExceptionHandler(ClientApiException.class)
    public ResponseEntity<ErrorMessage> handleClientApiException(ClientApiException e) {
        return ResponseEntity.status(e.getTargetStatus()).body(new ErrorMessage(e.getErrorMessage()));
    }

    @ExceptionHandler({RequestNotPermitted.class})
    public ResponseEntity<ErrorMessage> handleClientApiException(RequestNotPermitted e) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> handleException(Throwable e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Неизвестная ошибка"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintValidationException(ConstraintViolationException e) {
        List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationErrorResponse(violations));
    }

}
