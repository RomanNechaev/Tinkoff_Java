package tinkoff.training.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tinkoff.training.entities.Weather;
import tinkoff.training.models.WeatherModel;
import tinkoff.training.repositories.WeatherRepository;
import tinkoff.training.services.WeatherService;
import tinkoff.training.utils.exceptions.EntityExistsException;
import tinkoff.training.utils.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

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
        Weather weather = new Weather(weatherModel.name(), weatherModel.temperatureValue(), weatherModel.date(), weatherModel.time());
        weatherRepository.save(weather);
        return weather;
    }

    @Override
    public Weather update(String city, WeatherModel weatherModel) {
        Weather weather = weatherRepository
                .findByName(weatherModel.name())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
        return weatherRepository.update(new Weather(weather.getName(), weatherModel.temperatureValue(), weatherModel.date(), weatherModel.time()));

    }

    @Override
    public void delete(String city) {
        weatherRepository.deleteByName(city);
    }

    @Override
    public Weather getWeatherByCityName(String name) {
        return weatherRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
    }


}
