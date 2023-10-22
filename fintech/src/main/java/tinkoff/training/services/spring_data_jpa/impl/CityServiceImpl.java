package tinkoff.training.services.spring_data_jpa.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.City;
import tinkoff.training.repositories.spring_data_jpa.CityRepositoryJPA;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CrudService<City> {
    private final CityRepositoryJPA cityRepositoryJPA;

    @Override
    public List<City> findAll() {
        return cityRepositoryJPA.findAll();
    }

    @Override
    public City findById(Long id) {
        return cityRepositoryJPA.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public City create(City entity) {
        if (entity.getId() != null && cityRepositoryJPA.existsById(entity.getId())) {
            throw new EntityExistsException();
        }
        return cityRepositoryJPA.save(entity);
    }

    @Override
    public City update(Long id, City entity) {
        if (!cityRepositoryJPA.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return cityRepositoryJPA.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        cityRepositoryJPA.deleteById(id);
    }

    @Override
    public City findByName(String name) {
        Optional<City> city = cityRepositoryJPA.findCityByName(name);
        if (city.isEmpty()) {
            City newCity = new City(name);
            create(newCity);
            return newCity;
        } else return city.get();
    }
}
