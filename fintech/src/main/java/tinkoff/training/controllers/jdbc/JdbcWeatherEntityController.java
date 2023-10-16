package tinkoff.training.controllers.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.WeatherEntity;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("repository/jdbc/weather")
public class JdbcWeatherEntityController {
    private final JdbcCrudService<WeatherEntity> jdbcCrudService;

    @GetMapping
    public ResponseEntity<List<WeatherEntity>> getAllCities() {
        return ResponseEntity.ok(jdbcCrudService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherEntity> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(jdbcCrudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WeatherEntity> create(@RequestBody WeatherEntity weatherEntity) {
        return ResponseEntity.ok(jdbcCrudService.create(weatherEntity));
    }

    @PutMapping("{id}")
    public ResponseEntity<WeatherEntity> update(@PathVariable Long id, @RequestBody WeatherEntity weatherEntity) {
        return ResponseEntity.ok(jdbcCrudService.update(id, weatherEntity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
