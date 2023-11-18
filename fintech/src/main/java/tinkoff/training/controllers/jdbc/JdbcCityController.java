package tinkoff.training.controllers.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.City;
import tinkoff.training.mappers.CityListMapper;
import tinkoff.training.mappers.CityMapper;
import tinkoff.training.models.CityDto;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/repository/jdbc/city")
public class JdbcCityController {
    private final JdbcCrudService<City> jdbcCrudService;
    private final CityMapper cityMapper;
    private final CityListMapper cityListMapper;

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<CityDto> cityDtoList = cityListMapper.toDTOList(jdbcCrudService.findAll());
        return ResponseEntity.ok(cityDtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityMapper.toDTO(jdbcCrudService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CityDto> create(@RequestBody CityDto cityDto) {
        jdbcCrudService.create(cityMapper.toCity(cityDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CityDto> update(@PathVariable Long id, @RequestBody CityDto cityDto) {
        jdbcCrudService.update(id, cityMapper.toCity(cityDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jdbcCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
