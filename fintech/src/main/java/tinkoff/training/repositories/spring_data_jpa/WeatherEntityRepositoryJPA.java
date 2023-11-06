package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import tinkoff.training.entities.Weather;

public interface WeatherEntityRepositoryJPA extends JpaRepository<Weather, Long> {
}
