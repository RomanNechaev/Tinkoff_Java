package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tinkoff.training.entities.Weather;

@Repository
public interface WeatherEntityRepositoryJPA extends JpaRepository<Weather, Long> {
}
