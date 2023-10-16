package tinkoff.training.controllers.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.WeatherEntity;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("repository/jdbc/type")
public class JdbcWeatherTypeController {
    private final JdbcCrudService<WeatherType> weatherTypeJdbcCrudService;

    @GetMapping
    public ResponseEntity<List<WeatherType>> getAllCities() {
        return ResponseEntity.ok(weatherTypeJdbcCrudService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherType> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(weatherTypeJdbcCrudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WeatherType> create(@RequestBody WeatherType weatherType) {
        return ResponseEntity.ok(weatherTypeJdbcCrudService.create(weatherType));
    }

    @PutMapping("{id}")
    public ResponseEntity<WeatherType> update(@PathVariable Long id, @RequestBody WeatherType weatherType) {
        return ResponseEntity.ok(weatherTypeJdbcCrudService.update(id, weatherType));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        weatherTypeJdbcCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
