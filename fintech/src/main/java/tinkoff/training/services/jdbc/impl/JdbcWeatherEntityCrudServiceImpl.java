package tinkoff.training.services.jdbc.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.WeatherEntity;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JdbcWeatherEntityCrudServiceImpl implements JdbcCrudService<WeatherEntity> {

    private final CrudRepository<WeatherEntity> weatherEntityCrudRepository;

    @Override
    public WeatherEntity create(WeatherEntity weatherEntity) {
        weatherEntityCrudRepository.findById(weatherEntity.getId()).ifPresent(exception -> {
            throw new EntityExistsException();
        });
        return weatherEntityCrudRepository.save(weatherEntity);
    }

    @Override
    public WeatherEntity update(Long id, WeatherEntity weatherEntity) {
        if (weatherEntityCrudRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        }
        return weatherEntityCrudRepository.save(weatherEntity);
    }

    @Override
    public List<WeatherEntity> findAll() {
        return Collections.unmodifiableList(weatherEntityCrudRepository.findAll());
    }

    @Override
    public WeatherEntity findById(Long id) {
        return weatherEntityCrudRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        weatherEntityCrudRepository.deleteById(id);
    }
}
