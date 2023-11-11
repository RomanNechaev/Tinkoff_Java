package tinkoff.training.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class WeatherApiResponse {
    @JsonProperty("location")
    private Location location;
    @JsonProperty("current")
    private Current current;

    @Getter
    @Builder
    @Jacksonized
    public static class Location {
        @JsonProperty("name")
        private String name;
    }

    @Getter
    @Builder
    @Jacksonized
    public static class Current {
        @JsonProperty("last_updated")
        private String lastUpdated;
        @JsonProperty("temp_c")
        private double temperatureCelsius;
        @JsonProperty("condition")
        private Condition condition;

        @Getter
        @Jacksonized
        @Builder
        public static class Condition {
            @JsonProperty("text")
            private String weatherType;
        }
    }

}
