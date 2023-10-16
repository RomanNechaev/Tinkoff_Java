package tinkoff.training.services.spring_data_jpa.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.spring_data_jpa.WeatherTypeRepository;
import tinkoff.training.services.spring_data_jpa.CrudService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherTypeServiceImpl implements CrudService<WeatherType> {
    private final WeatherTypeRepository weatherTypeRepository;

    @Override
    public List<WeatherType> findAll() {
        return weatherTypeRepository.findAll();
    }

    @Override
    public WeatherType findById(Long id) {
        return weatherTypeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public WeatherType create(WeatherType entity) {
        if (weatherTypeRepository.existsById(entity.getId())) {
            throw new EntityExistsException();
        }
        return weatherTypeRepository.save(entity);
    }

    @Override
    public WeatherType update(Long id, WeatherType entity) {
        if (!weatherTypeRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        return weatherTypeRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        weatherTypeRepository.deleteById(id);
    }
}
