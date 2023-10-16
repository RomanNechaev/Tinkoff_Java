package tinkoff.training.services.spring_data_jpa.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.WeatherEntity;
import tinkoff.training.repositories.spring_data_jpa.WeatherEntityRepository;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherEntityServiceImpl implements CrudService<WeatherEntity> {
    private final WeatherEntityRepository weatherEntityRepository;

    @Override
    public List<WeatherEntity> findAll() {
        return weatherEntityRepository.findAll();
    }

    @Override
    public WeatherEntity findById(Long id) {
        return weatherEntityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WeatherEntity create(WeatherEntity entity) {
        if (weatherEntityRepository.existsById(entity.getId())) {
            throw new EntityExistsException();
        }
        return weatherEntityRepository.save(entity);
    }

    @Override
    public WeatherEntity update(Long id, WeatherEntity entity) {
        if (!weatherEntityRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return weatherEntityRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        weatherEntityRepository.deleteById(id);
    }
}
