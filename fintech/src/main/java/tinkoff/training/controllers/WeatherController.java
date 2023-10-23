package tinkoff.training.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.services.WeatherApiService;
import tinkoff.training.services.WeatherService;

@RestController
@RequestMapping("/api/weather/{city}")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final WeatherApiService weatherApiService;

    @GetMapping("/info")
    @RateLimiter(name = "info")
    public Mono<ResponseEntity<WeatherModel>> getWeatherByCityName(@PathVariable String city) {
        return weatherApiService.getWeatherByCityName(city).map(ResponseEntity::ok);
    }

    @Operation(description = "Get current temperature for city",
            responses = {
                    @ApiResponse(responseCode = "200", description = "temperature for city"),
                    @ApiResponse(responseCode = "404", description = "City not found")
            }
    )

    @GetMapping
    public ResponseEntity<Double> getTemperatureByCityName(@PathVariable String city) {
        return ResponseEntity.ok(weatherService.getWeatherByCityName(city).getTemperatureValue());
    }

    @Operation(description = "Add weather data for a specific city",
            responses = {
                    @ApiResponse(responseCode = "200", description = "weather create successfully"),
                    @ApiResponse(responseCode = "400", description = "invalid parameters to create"),
                    @ApiResponse(responseCode = "409", description = "weather data already exists"),
                    @ApiResponse(responseCode = "422", description = "city name non match with data request")
            }
    )

    @PostMapping
    public ResponseEntity<Weather> addWeatherByCityName(@PathVariable String city, @RequestBody WeatherModel weatherModel) {
        return ResponseEntity.ok(weatherService.create(city, weatherModel));
    }

    @Operation(description = "Update weather data for a specific city",
            responses = {
                    @ApiResponse(responseCode = "200", description = "weather update successfully"),
                    @ApiResponse(responseCode = "400", description = "invalid parameters to update"),
                    @ApiResponse(responseCode = "404", description = "City not found"),
                    @ApiResponse(responseCode = "422", description = "city name non match with data request")
            }
    )

    @PutMapping
    public ResponseEntity<Weather> updateWeatherByCityName(@PathVariable String city, @RequestBody WeatherModel weatherModel) {
        return ResponseEntity.ok(weatherService.update(city, weatherModel));
    }

    @Operation(description = "Delete weather data for a specific city",
            responses = {
                    @ApiResponse(responseCode = "200", description = "weather for city delete successfully"),
            }
    )

    @DeleteMapping
    public ResponseEntity<Void> deleteWeatherByCityName(@PathVariable String city) {
        weatherService.delete(city);
        return ResponseEntity.ok().build();
    }
}
