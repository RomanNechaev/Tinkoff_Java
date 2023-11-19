package tinkoff.training.services.spring_data_jpa.impl;

import tinkoff.training.utils.cache.WeatherCache;
import tinkoff.training.utils.exceptions.application.EntityExistsException;
import tinkoff.training.utils.exceptions.application.EntityNotFoundException;
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
    private final WeatherCache weatherCache;

    @Override
    public List<Weather> findAll() {
        return weatherEntityRepositoryJPA.findAll();
    }

    @Override
    public Weather findById(Long id) {
        var weatherFromCache = weatherCache.get(id);
        if (weatherFromCache.isPresent()) {
            return weatherFromCache.get();
        }
        Weather weather = weatherEntityRepositoryJPA
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weather with input id not found!"));
        weatherCache.put(id, weather);
        return weather;
    }

    @Override
    public Weather create(Weather entity) {
        if (entity.getId() != null && weatherEntityRepositoryJPA.existsById(entity.getId())) {
            throw new EntityExistsException("Weather already exists!");
        }
        return weatherEntityRepositoryJPA.save(entity);
    }

    @Override
    public Weather update(Long id, Weather entity) {
        if (!weatherEntityRepositoryJPA.existsById(id)) {
            throw new EntityNotFoundException("Weather already exists!");
        }
        weatherCache.delete(id);
        Weather updatedWeather = weatherEntityRepositoryJPA.save(entity);
        weatherCache.put(id, updatedWeather);
        return updatedWeather;
    }

    @Override
    public void deleteById(Long id) {
        weatherEntityRepositoryJPA.deleteById(id);
        weatherCache.delete(id);
    }

    @Override
    public Weather findByName(String name) {
        return null;
    }
}
