package tinkoff.training.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.repositories.WeatherRepository;
import tinkoff.training.repositories.spring_data_jpa.CityRepositoryJPA;
import tinkoff.training.repositories.spring_data_jpa.WeatherTypeRepositoryJPA;
import tinkoff.training.services.WeatherService;
import tinkoff.training.utils.exceptions.application.EntityExistsException;
import tinkoff.training.utils.exceptions.application.EntityNotFoundException;
import tinkoff.training.utils.exceptions.application.NonMatchDataException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;
    private final WeatherTypeRepositoryJPA weatherTypeRepositoryJPA;
    private final CityRepositoryJPA cityRepositoryJPA;

    public double getAverageTemperature() {
        return weatherRepository.findAll().stream().map(Weather::getTemperature).collect(Collectors.averagingDouble(x -> x));
    }

    public List<String> findRegionsAboveTemperature(double temperature) {
        return weatherRepository.findAll().stream().filter(x -> x.getTemperature() > temperature).map(x->x.getCity().getName()).toList();
    }

    public Map<Long, List<Double>> idToTemperature() {
        return weatherRepository.findAll().stream().collect(Collectors.groupingBy(Weather::getId, Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public Map<Double, List<Weather>> toSameWeathereMap() {
        return weatherRepository.findAll().stream().collect(Collectors.groupingBy(Weather::getTemperature, Collectors.toList()));
    }

    @Override
    public Weather create(String city, WeatherDto weatherDto) {
        if (weatherRepository.existsByName(city)) {
            throw new EntityExistsException("Weather Entity already exists!");
        }
        checkMatchRegionName(city, weatherDto);
        WeatherType weatherType = weatherTypeRepositoryJPA
                .findWeatherTypeByType(weatherDto.getType())
                .orElse(weatherTypeRepositoryJPA.save(new WeatherType(weatherDto.getType())));
        City cityEntity = cityRepositoryJPA
                .findCityByName(weatherDto.getCity())
                .orElse(cityRepositoryJPA.save(new City(weatherDto.getCity())));
        Weather weather = new Weather(weatherDto.getTemperature(),weatherDto.getDate(),weatherDto.getTime(),cityEntity,weatherType);
        weatherRepository.save(weather);
        return weather;
    }

    @Override
    public Weather update(String city, WeatherDto weatherDto) {
        Weather weather = weatherRepository
                .findByName(weatherDto.getCity())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
        checkMatchRegionName(city, weatherDto);
        WeatherType weatherType = weatherTypeRepositoryJPA
                .findWeatherTypeByType(weatherDto.getType())
                .orElse(weatherTypeRepositoryJPA.save(new WeatherType(weatherDto.getType())));
        return weatherRepository.update(new Weather(weatherDto.getTemperature(), weatherDto.getDate(), weatherDto.getTime(),weather.getCity(),weatherType));

    }

    @Override
    public void delete(String city) {
        weatherRepository.deleteByName(city);
    }

    @Override
    public Weather getWeatherByCityName(String name) {
        return weatherRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
    }

    private void checkMatchRegionName(String city, WeatherDto weatherDto) {
        if (!Objects.equals(city, weatherDto.getCity())) {
            throw new NonMatchDataException("Данные названия региона в модели и запросе не совпадают!");
        }
    }
}
