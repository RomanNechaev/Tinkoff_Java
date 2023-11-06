package tinkoff.training.controllers.spring_data_jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tinkoff.training.entities.City;
import tinkoff.training.mappers.CityListMapper;
import tinkoff.training.mappers.CityMapper;
import tinkoff.training.models.CityDto;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repository/jpa/city")
public class CityController {
    private final CrudService<City> cityCrudService;
    private final CityMapper cityMapper;
    private final CityListMapper cityListMapper;

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        List<CityDto> cityDtoList = cityListMapper.toDTOList(cityCrudService.findAll());
        return ResponseEntity.ok(cityDtoList);
    }

    @GetMapping("{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityMapper.toDTO(cityCrudService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CityDto> create(@RequestBody CityDto cityDto) {
        cityCrudService.create(cityMapper.toCity(cityDto));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<CityDto> update(@PathVariable Long id, @RequestBody CityDto cityDto) {
        cityCrudService.update(id, cityMapper.toCity(cityDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cityCrudService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
