package tinkoff.training.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.Weather;
import tinkoff.training.kafka.KafkaProducer;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.services.WeatherApiService;


@Component
public class WeatherScheduler {
    private final WeatherApiService weatherApiService;
    private final WeatherMapper weatherMapper;
    private final String[] cityList;
    private final KafkaProducer kafkaProducer;
    private int currentCityIndex = 0;

    @Autowired
    private WeatherScheduler(WeatherApiService weatherApiService, WeatherMapper weatherMapper, @Value("${api.scheduler.weather.cities}") String cities, KafkaProducer kafkaProducer) {
        this.weatherApiService = weatherApiService;
        this.weatherMapper = weatherMapper;
        cityList = cities.split(",");
        this.kafkaProducer = kafkaProducer;
    }

    @Scheduled(cron = "${api.scheduler.weather.frequency}")
    public void getWeatherInCities() {
        String city = cityList[currentCityIndex % cityList.length];
        Weather weather = weatherApiService.getWeatherByCityName(city);
        kafkaProducer.produce(weatherMapper.toDTO(weather));
        currentCityIndex += 1;
    }
}
