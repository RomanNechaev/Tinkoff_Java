package tinkoff.training.services.jdbc.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.services.jdbc.JdbcCrudService;
import tinkoff.training.utils.exceptions.application.EntityNotFoundException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcWeatherTypeCrudServiceImpl implements JdbcCrudService<WeatherType> {

    private final CrudRepository<WeatherType> weatherTypeRepository;

    @Override
    public WeatherType create(WeatherType weatherType) {
        if(weatherTypeRepository.findById(weatherType.getId()).isPresent()){
            throw new EntityNotFoundException("Weather type already exists!");
        }
        return weatherTypeRepository.save(weatherType);
    }

    @Override
    public WeatherType update(Long id, WeatherType weatherType) {
        if (weatherTypeRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Weather Type not found!");
        }
        return weatherTypeRepository.save(weatherType);
    }

    @Override
    public List<WeatherType> findAll() {
        return Collections.unmodifiableList(weatherTypeRepository.findAll());
    }

    @Override
    public WeatherType findById(Long id) {
        return weatherTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Weather Type not found!"));
    }

    @Override
    public void deleteById(Long id) {
        weatherTypeRepository.deleteById(id);
    }
}
