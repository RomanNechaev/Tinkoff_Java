package tinkoff.training.controllers.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.City;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("repository/jdbc/city")
public class JdbcCityController {
    private final JdbcCrudService<City> jdbcCrudService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(jdbcCrudService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(jdbcCrudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<City> create(@RequestBody City city) {
        return ResponseEntity.ok(jdbcCrudService.create(city));
    }

    @PutMapping("{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @RequestBody City city) {
        return ResponseEntity.ok(jdbcCrudService.update(id, city));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
