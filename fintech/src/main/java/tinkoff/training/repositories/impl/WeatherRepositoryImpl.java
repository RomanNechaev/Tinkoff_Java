package tinkoff.training.repositories.impl;

import org.springframework.stereotype.Repository;
import tinkoff.training.entities.Weather;
import tinkoff.training.repositories.WeatherRepository;
import tinkoff.training.utils.exceptions.application.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

    private final List<Weather> weatherList = new ArrayList<>();

    @Override
    public List<Weather> findAll() {
        return weatherList;
    }

    @Override
    public Optional<Weather> findByName(String name) {
        return weatherList.stream().filter(x -> Objects.equals(x.getCity(), name)).findFirst();
    }

    @Override
    public void deleteByName(String name) {
        weatherList.removeIf(x -> Objects.equals(x.getCity(), name));
    }

    @Override
    public boolean existsByName(String name) {
        return weatherList.stream().anyMatch(x -> Objects.equals(x.getCity(), name));
    }

    @Override
    public Weather save(Weather weather) {
        if (weather.getCity() == null) {
            throw new IllegalStateException("Region name must be not null!");
        }
        weatherList.add(weather);
        return weather;
    }

    @Override
    public Weather update(Weather weather) {
        if (!existsByName(weather.getCity().getName())) {
            throw new EntityNotFoundException("Entity not found!");
        }
        Weather oldWeather = findByName(weather.getCity().getName()).get();
        int index = weatherList.indexOf(oldWeather);
        weatherList.set(index, weather);
        return weather;
    }


}
