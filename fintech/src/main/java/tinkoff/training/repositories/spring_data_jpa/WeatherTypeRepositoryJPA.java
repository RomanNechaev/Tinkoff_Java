package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.repository.ListCrudRepository;
import tinkoff.training.entities.WeatherType;

import java.util.Optional;

public interface WeatherTypeRepositoryJPA extends ListCrudRepository<WeatherType, Long> {
    Optional<WeatherType> findWeatherTypeByType(String type);
}
