package tinkoff.training.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WeatherApiResponse {
    @JsonProperty("location")
    private Location location;
    @JsonProperty("current")
    private Current current;

    @Getter
    public static class Location {
        @JsonProperty("name")
        private String name;
    }

    @Getter
    public static class Current {
        @JsonProperty("last_updated")
        private String lastUpdated;
        @JsonProperty("temp_c")
        private double temperatureCelsius;
    }

}
