package tinkoff.training.utils.exceptions;

import lombok.Getter;
import tinkoff.training.models.WeatherApiError;

@Getter
public class ApiException extends RuntimeException {
    private final WeatherApiError weatherApiError;
    private final int httpCode;

    public ApiException(WeatherApiError weatherApiError, int httpCode) {
        this.weatherApiError = weatherApiError;
        this.httpCode = httpCode;
    }
}
