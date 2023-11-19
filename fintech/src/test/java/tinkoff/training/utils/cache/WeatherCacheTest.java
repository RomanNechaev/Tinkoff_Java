package tinkoff.training.utils.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource
class WeatherCacheTest {
    private Weather testWeather1;
    private Weather testWeather2;
    private Weather testWeather3;
    private City testCity1;
    private City testCity2;
    private City testCity3;

    private WeatherType testWeatherType;
    private WeatherCache weatherCache;

    @BeforeEach
    void setUp() {
        weatherCache = new WeatherCache();
        ReflectionTestUtils.setField(weatherCache, "capacity", 2);

        weatherCache.clear();
        testCity1 = new City();
        testCity1.setId(1L);
        testCity1.setName("test");
        testWeatherType = new WeatherType();
        testWeatherType.setType("test");
        testWeatherType.setId(1L);
        testWeather1 = new Weather();
        testWeather1.setId(1L);
        testWeather1.setType(testWeatherType);
        testWeather1.setTemperature(13.0);
        testWeather1.setDate("2023-04-19");
        testWeather1.setTime("14:03:00");

        testCity2 = new City();
        testCity2.setId(1L);
        testCity2.setName("test2");
        testWeather2 = new Weather();
        testWeather2.setId(2L);
        testWeather2.setType(testWeatherType);
        testWeather2.setTemperature(14.0);
        testWeather2.setDate("2023-05-19");
        testWeather2.setTime("15:03:00");

        testCity3 = new City();
        testCity3.setId(3L);
        testCity3.setName("test2");
        testWeather3 = new Weather();
        testWeather3.setId(3L);
        testWeather3.setType(testWeatherType);
        testWeather3.setTemperature(15.0);
        testWeather3.setDate("2023-05-21");
        testWeather3.setTime("15:21:00");
    }

    @Test
    void canGetWithEmptyCacheShouldReturnEmptyOptionalFromCache() {
        //cacheIsEmpty

        Optional<Weather> result = weatherCache.get(testWeather1.getId());

        assertThat(result).isNotPresent();
    }

    @Test
    void canGetIfCacheIsOverflowShouldReturnEmptyOptional() {
        weatherCache.put(testWeather1.getId(), testWeather1);
        weatherCache.put(testWeather2.getId(), testWeather2);
        weatherCache.put(testWeather3.getId(), testWeather3);

        Optional<Weather> result = weatherCache.get(testWeather1.getId());

        assertThat(result).isNotPresent();
    }

    @Test
    void canGetWithExistentIdShouldReturnWeatherFromCache() {
        weatherCache.put(1L, testWeather1);

        Optional<Weather> result = weatherCache.get(1L);

        assertThat(result).isEqualTo(Optional.of(testWeather1));
    }

    @Test
    void canClearCacheAndGetWeatherShouldReturnEmptyOptional() {
        weatherCache.put(testWeather1.getId(), testWeather1);
        weatherCache.put(testWeather2.getId(), testWeather2);

        weatherCache.clear();

        Optional<Weather> result = weatherCache.get(testWeather1.getId());

        assertThat(result).isNotPresent();
    }

    @Test
    void canDeleteWeatherFromCache() {
        weatherCache.put(testWeather1.getId(), testWeather1);
        weatherCache.put(testWeather2.getId(), testWeather2);

        weatherCache.delete(testWeather1.getId());

        Optional<Weather> result = weatherCache.get(testWeather1.getId());

        assertThat(result).isNotPresent();
    }

}