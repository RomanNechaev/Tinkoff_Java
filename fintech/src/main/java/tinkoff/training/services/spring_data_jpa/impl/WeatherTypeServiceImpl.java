package tinkoff.training.services.spring_data_jpa.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.spring_data_jpa.WeatherTypeRepositoryJPA;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherTypeServiceImpl implements CrudService<WeatherType> {
    private final WeatherTypeRepositoryJPA weatherTypeRepositoryJPA;

    @Override
    public List<WeatherType> findAll() {
        return weatherTypeRepositoryJPA.findAll();
    }

    @Override
    public WeatherType findById(Long id) {
        return weatherTypeRepositoryJPA.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WeatherType create(WeatherType entity) {
        if (entity.getId() != null && weatherTypeRepositoryJPA.existsById(entity.getId())) {
            throw new EntityExistsException();
        }
        return weatherTypeRepositoryJPA.save(entity);
    }

    @Override
    public WeatherType update(Long id, WeatherType entity) {
        if (!weatherTypeRepositoryJPA.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return weatherTypeRepositoryJPA.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        weatherTypeRepositoryJPA.deleteById(id);
    }

    @Override
    public WeatherType findByName(String name) {
        Optional<WeatherType> weatherType = weatherTypeRepositoryJPA.findWeatherTypeByType(name);
        if (weatherType.isEmpty()) {
            WeatherType newWeatherType = new WeatherType(name);
            create(newWeatherType);
            return newWeatherType;
        } else return weatherType.get();
    }
}
