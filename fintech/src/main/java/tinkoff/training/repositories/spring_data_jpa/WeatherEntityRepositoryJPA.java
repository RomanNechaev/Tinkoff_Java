package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.repository.ListCrudRepository;
import tinkoff.training.entities.WeatherEntity;

public interface WeatherEntityRepositoryJPA extends ListCrudRepository<WeatherEntity, Long> {
}
