package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import tinkoff.training.entities.WeatherType;

import java.util.Optional;

public interface WeatherTypeRepositoryJPA extends JpaRepository<WeatherType, Long> {
    Optional<WeatherType> findWeatherTypeByType(String type);
}
