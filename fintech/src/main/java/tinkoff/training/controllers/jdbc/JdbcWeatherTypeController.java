package tinkoff.training.controllers.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.WeatherTypeListMapper;
import tinkoff.training.mappers.WeatherTypeMapper;
import tinkoff.training.models.WeatherTypeDto;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/repository/jdbc/type")
public class JdbcWeatherTypeController {
    private final JdbcCrudService<WeatherType> weatherTypeJdbcCrudService;
    private final WeatherTypeMapper weatherTypeMapper;
    private final WeatherTypeListMapper weatherTypeListMapper;

    @GetMapping
    public ResponseEntity<List<WeatherTypeDto>> getAllCities() {
        List<WeatherTypeDto> weatherDtoList = weatherTypeListMapper.toDTOList(weatherTypeJdbcCrudService.findAll());
        return ResponseEntity.ok(weatherDtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherTypeDto> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(weatherTypeMapper.toDTO(weatherTypeJdbcCrudService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<WeatherTypeDto> create(@RequestBody WeatherTypeDto weatherTypeDto) {
        weatherTypeJdbcCrudService.create(weatherTypeMapper.toWeatherType(weatherTypeDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<WeatherTypeDto> update(@PathVariable Long id, @RequestBody WeatherTypeDto weatherTypeDto) {
        weatherTypeJdbcCrudService.update(id, weatherTypeMapper.toWeatherType(weatherTypeDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        weatherTypeJdbcCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
