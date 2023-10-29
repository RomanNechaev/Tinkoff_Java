package tinkoff.training.services.spring_data_jpa.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.Weather;
import tinkoff.training.repositories.spring_data_jpa.WeatherEntityRepositoryJPA;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherEntityServiceImpl implements CrudService<Weather> {
    private final WeatherEntityRepositoryJPA weatherEntityRepositoryJPA;

    @Override
    public List<Weather> findAll() {
        return weatherEntityRepositoryJPA.findAll();
    }

    @Override
    public Weather findById(Long id) {
        return weatherEntityRepositoryJPA.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Weather create(Weather entity) {
        if (entity.getId() != null && weatherEntityRepositoryJPA.existsById(entity.getId())) {
            throw new EntityExistsException();
        }
        return weatherEntityRepositoryJPA.save(entity);
    }

    @Override
    public Weather update(Long id, Weather entity) {
        if (!weatherEntityRepositoryJPA.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return weatherEntityRepositoryJPA.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        weatherEntityRepositoryJPA.deleteById(id);
    }

    @Override
    public Weather findByName(String name) {
        return null;
    }
}
