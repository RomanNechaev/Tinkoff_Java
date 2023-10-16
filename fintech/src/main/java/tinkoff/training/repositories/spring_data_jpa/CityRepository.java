package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.repository.ListCrudRepository;
import tinkoff.training.entities.City;

public interface CityRepository extends ListCrudRepository<City, Long> {
}
