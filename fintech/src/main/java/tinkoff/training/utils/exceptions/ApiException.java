package tinkoff.training.utils.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tinkoff.training.models.WeatherApiError;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {
    private final WeatherApiError weatherApiError;
    private final int httpCode;
}
