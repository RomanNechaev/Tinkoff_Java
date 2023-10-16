package tinkoff.training.controllers.spring_data_jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.WeatherEntity;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("repository/jpa/weather")
public class WeatherEntityController {

    private final CrudService<WeatherEntity> weatherEntityCrudService;

    @GetMapping
    public ResponseEntity<List<WeatherEntity>> getAllCities() {
        return ResponseEntity.ok(weatherEntityCrudService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherEntity> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(weatherEntityCrudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WeatherEntity> create(@RequestBody WeatherEntity weatherEntity) {
        return ResponseEntity.ok(weatherEntityCrudService.create(weatherEntity));
    }

    @PutMapping("{id}")
    public ResponseEntity<WeatherEntity> update(@PathVariable Long id, @RequestBody WeatherEntity weatherEntity) {
        return ResponseEntity.ok(weatherEntityCrudService.update(id, weatherEntity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        weatherEntityCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
