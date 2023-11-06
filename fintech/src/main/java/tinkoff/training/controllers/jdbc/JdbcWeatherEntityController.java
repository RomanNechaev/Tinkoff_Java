package tinkoff.training.controllers.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.Weather;
import tinkoff.training.mappers.WeatherListMapper;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repository/jdbc/weather")
public class JdbcWeatherEntityController {
    private final JdbcCrudService<Weather> jdbcCrudService;
    private final WeatherListMapper weatherListMapper;
    private final WeatherMapper weatherMapper;

    @GetMapping
    public ResponseEntity<List<WeatherDto>> getAllCities() {
        List<WeatherDto> weatherDtoList = weatherListMapper.toDTOList(jdbcCrudService.findAll());
        return ResponseEntity.ok(weatherDtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherDto> getCityById(@PathVariable Long id) {
        WeatherDto weatherDto = weatherMapper.toDTO(jdbcCrudService.findById(id));
        return ResponseEntity.ok(weatherDto);
    }

    @PostMapping
    public ResponseEntity<WeatherDto> create(@RequestBody WeatherDto weatherDto) {
        jdbcCrudService.create(weatherMapper.toWeather(weatherDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<WeatherDto> update(@PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        jdbcCrudService.update(id, weatherMapper.toWeather(weatherDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
