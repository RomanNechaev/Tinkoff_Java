package tinkoff.training.controllers.spring_data_jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.WeatherTypeListMapper;
import tinkoff.training.mappers.WeatherTypeMapper;
import tinkoff.training.models.WeatherTypeDto;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/repository/jpa/type")
public class WeatherTypeController {
    private final CrudService<WeatherType> weatherTypeCrudService;
    private final WeatherTypeMapper weatherTypeMapper;
    private final WeatherTypeListMapper weatherTypeListMapper;

    @GetMapping
    public ResponseEntity<List<WeatherTypeDto>> getAllCities() {
        List<WeatherTypeDto> weatherDtoList = weatherTypeListMapper.toDTOList(weatherTypeCrudService.findAll());
        return ResponseEntity.ok(weatherDtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<WeatherTypeDto> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(weatherTypeMapper.toDTO(weatherTypeCrudService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<WeatherType> create(@RequestBody WeatherTypeDto weatherTypeDto) {
        weatherTypeCrudService.create(weatherTypeMapper.toWeatherType(weatherTypeDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<WeatherType> update(@PathVariable Long id, @RequestBody WeatherTypeDto weatherTypeDto) {
        weatherTypeCrudService.update(id, weatherTypeMapper.toWeatherType(weatherTypeDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        weatherTypeCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
