package tinkoff.training.controllers.spring_data_jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("repository/jpa/type")
public class WeatherTypeController {
    private final CrudService<WeatherType> weatherTypeCrudService;

    @GetMapping
    public ResponseEntity<List<WeatherType>> getAllCities() {
        return ResponseEntity.ok(weatherTypeCrudService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherType> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(weatherTypeCrudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<WeatherType> create(@RequestBody WeatherType weatherType) {
        return ResponseEntity.ok(weatherTypeCrudService.create(weatherType));
    }

    @PutMapping("{id}")
    public ResponseEntity<WeatherType> update(@PathVariable Long id, @RequestBody WeatherType weatherType) {
        return ResponseEntity.ok(weatherTypeCrudService.update(id, weatherType));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        weatherTypeCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
