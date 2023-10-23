package tinkoff.training.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WeatherApiError {
    @JsonProperty("error")
    private ApiError apiError;

    @Getter
    public static class ApiError {
        @JsonProperty("code")
        private int code;
        @JsonProperty("message")
        private String message;

    }
}
