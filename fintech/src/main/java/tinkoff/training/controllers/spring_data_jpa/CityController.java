package tinkoff.training.controllers.spring_data_jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.City;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("repository/jpa/city")
public class CityController {
    private final CrudService<City> cityCrudService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityCrudService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityCrudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<City> create(@RequestBody City city) {
        return ResponseEntity.ok(cityCrudService.create(city));
    }

    @PutMapping("{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @RequestBody City city) {
        return ResponseEntity.ok(cityCrudService.update(id, city));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
