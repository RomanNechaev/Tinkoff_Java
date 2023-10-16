package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.repository.ListCrudRepository;
import tinkoff.training.entities.WeatherType;

public interface WeatherTypeRepository extends ListCrudRepository<WeatherType, Long> {
}
