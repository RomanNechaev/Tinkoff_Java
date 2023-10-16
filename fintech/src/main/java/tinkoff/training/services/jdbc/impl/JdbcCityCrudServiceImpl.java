package tinkoff.training.services.jdbc.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.City;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.services.jdbc.JdbcCrudService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcCityCrudServiceImpl implements JdbcCrudService<City> {

    private final CrudRepository<City> cityRepository;

    @Override
    public City create(City city) {
        cityRepository.findById(city.getId()).ifPresent(exception -> {
            throw new EntityExistsException();
        });
        return cityRepository.save(city);
    }

    @Override
    public City update(Long id, City city) {
        if (cityRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException();
        }
        return cityRepository.update(city);
    }

    @Override
    public List<City> findAll() {
        return Collections.unmodifiableList(cityRepository.findAll());
    }

    @Override
    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
