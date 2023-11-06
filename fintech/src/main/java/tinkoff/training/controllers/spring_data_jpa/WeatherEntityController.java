package tinkoff.training.controllers.spring_data_jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.Weather;
import tinkoff.training.mappers.WeatherListMapper;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("repository/jpa/weather")
public class WeatherEntityController {

    private final CrudService<Weather> weatherEntityCrudService;
    private final WeatherListMapper weatherListMapper;
    private final WeatherMapper weatherMapper;

    @GetMapping
    public ResponseEntity<List<WeatherDto>> getAllWeather() {
        List<WeatherDto> weatherDtoList = weatherListMapper.toDTOList(weatherEntityCrudService.findAll());
        return ResponseEntity.ok(weatherDtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherDto> getWeatherById(@PathVariable Long id) {
        WeatherDto weatherDto = weatherMapper.toDTO(weatherEntityCrudService.findById(id));
        return ResponseEntity.ok(weatherDto);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody WeatherDto weatherDto) {
        weatherEntityCrudService.create(weatherMapper.toWeather(weatherDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody WeatherDto weatherDto) {
        weatherEntityCrudService.update(id, weatherMapper.toWeather(weatherDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        weatherEntityCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
