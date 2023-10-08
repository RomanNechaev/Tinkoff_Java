package tinkoff.training.repositories;

import tinkoff.training.entities.Weather;

import java.util.List;
import java.util.Optional;


public interface WeatherRepository {
    List<Weather> findAll();

    Optional<Weather> findByName(String name);

    void deleteByName(String name);

    boolean existsByName(String name);

    Weather save(Weather weather);

    Weather update(Weather weather);


}
