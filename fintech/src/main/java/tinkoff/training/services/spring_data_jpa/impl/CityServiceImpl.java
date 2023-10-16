package tinkoff.training.services.spring_data_jpa.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.City;
import tinkoff.training.repositories.spring_data_jpa.CityRepository;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CrudService<City> {
    private final CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public City create(City entity) {
        if (cityRepository.existsById(entity.getId())) {
            throw new EntityExistsException();
        }
        return cityRepository.save(entity);
    }

    @Override
    public City update(Long id, City entity) {
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return cityRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }
}
