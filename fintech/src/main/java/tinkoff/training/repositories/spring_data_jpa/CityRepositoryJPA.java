package tinkoff.training.repositories.spring_data_jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import tinkoff.training.entities.City;

import java.util.Optional;

public interface CityRepositoryJPA extends JpaRepository<City, Long> {
    Optional<City> findCityByName(String name);
}
