package tinkoff.training.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.repositories.WeatherRepository;
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

    public double getAverageTemperature() {
        return weatherRepository.findAll().stream().map(Weather::getTemperatureValue).collect(Collectors.averagingDouble(x -> x));
    }

    public List<String> findRegionsAboveTemperature(double temperature) {
        return weatherRepository.findAll().stream().filter(x -> x.getTemperatureValue() > temperature).map(Weather::getName).toList();
    }

    public Map<UUID, List<Double>> idToTemperature() {
        return weatherRepository.findAll().stream().collect(Collectors.groupingBy(Weather::getId, Collectors.mapping(Weather::getTemperatureValue, Collectors.toList())));
    }

    public Map<Double, List<Weather>> toSameWeathereMap() {
        return weatherRepository.findAll().stream().collect(Collectors.groupingBy(Weather::getTemperatureValue, Collectors.toList()));
    }

    @Override
    public Weather create(String city, WeatherModel weatherModel) {
        if (weatherRepository.existsByName(city)) {
            throw new EntityExistsException("Weather Entity already exists!");
        }
        checkMatchRegionName(city, weatherModel);
        Weather weather = new Weather(weatherModel.getName(), weatherModel.getTemperatureValue(), weatherModel.getDate(), weatherModel.getTime());
        weatherRepository.save(weather);
        return weather;
    }

    @Override
    public Weather update(String city, WeatherModel weatherModel) {
        Weather weather = weatherRepository
                .findByName(weatherModel.getName())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
        checkMatchRegionName(city, weatherModel);
        return weatherRepository.update(new Weather(weather.getName(), weatherModel.getTemperatureValue(), weatherModel.getDate(), weatherModel.getTime()));

    }

    @Override
    public void delete(String city) {
        weatherRepository.deleteByName(city);
    }

    @Override
    public Weather getWeatherByCityName(String name) {
        return weatherRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
    }

    private void checkMatchRegionName(String city, WeatherModel weatherModel) {
        if (!Objects.equals(city, weatherModel.getName())) {
            throw new NonMatchDataException("Данные названия региона в модели и запросе не совпадают!");
        }
    }
}
