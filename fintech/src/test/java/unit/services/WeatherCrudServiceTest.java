package unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.spring_data_jpa.WeatherEntityRepositoryJPA;
import tinkoff.training.services.impl.WeatherServiceImpl;
import tinkoff.training.services.spring_data_jpa.CrudService;
import tinkoff.training.services.spring_data_jpa.impl.WeatherEntityServiceImpl;
import tinkoff.training.utils.cache.WeatherCache;
import tinkoff.training.utils.exceptions.application.EntityExistsException;
import tinkoff.training.utils.exceptions.application.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {WeatherServiceImpl.class})
@ExtendWith(MockitoExtension.class)
public class WeatherCrudServiceTest {
    @Mock
    WeatherEntityRepositoryJPA weatherEntityRepositoryJPA;
    @Mock
    WeatherCache weatherCache;

    private CrudService<Weather> weatherService;
    private Weather testWeather1;
    private Weather testWeather2;
    private Weather testWeather3;
    private City testCity1;


    @BeforeEach
    void setUp() {
        weatherService = new WeatherEntityServiceImpl(weatherEntityRepositoryJPA, weatherCache);
        testCity1 = new City(1L, "Perm");
        City testCity2 = new City(2L, "Grozny");
        City testCity3 = new City(3L, "Ekaterinburg");
        WeatherType testWeatherType1 = new WeatherType(1L, "cloudy");
        WeatherType testWeatherType2 = new WeatherType(2L, "rainy");
        testWeather1 = new Weather(1L, 12.4, "12-04-2023", "14:03", testCity1, testWeatherType1);
        testWeather2 = new Weather(2L, 24.4, "12-08-2023", "19:03", testCity2, testWeatherType2);
        testWeather3 = new Weather(3L, -3.0, "12-01-2023", "21:21", testCity3, testWeatherType1);
    }

    @Test
    void canGetAllWeatherShouldReturnEntities() {
        List<Weather> weatherList = List.of(
                testWeather1,
                testWeather2,
                testWeather3);


        given(weatherEntityRepositoryJPA.findAll())
                .willReturn(weatherList);

        weatherService.findAll();
        verify(weatherEntityRepositoryJPA, times(1)).findAll();
    }

    @Test
    void canGetWeatherIfIdExistShouldReturnWeather() {
        Long testId = testWeather1.getId();
        given(weatherEntityRepositoryJPA.findById(testId)).willReturn(Optional.of(testWeather1));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        weatherService.findById(testId);

        verify(weatherEntityRepositoryJPA, times(1)).findById(argument.capture());
        assertThat(argument.getValue()).isEqualTo(testId);
    }

    @Test
    void canGetWeatherIfIdDontExistsShroudThrowException() {
        Long testId = 12L;
        given(weatherEntityRepositoryJPA.findById(testId)).willReturn(Optional.empty());
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        assertThatThrownBy(() -> weatherService.findById(testId)).isInstanceOf(EntityNotFoundException.class);

        verify(weatherEntityRepositoryJPA, times(1)).findById(argument.capture());
    }

    @Test
    void canCreateWeatherShouldReturnWeather() {
        given(weatherEntityRepositoryJPA.existsById(testWeather1.getId())).willReturn(false);

        ArgumentCaptor<Weather> weatherArgumentCaptor = ArgumentCaptor.forClass(Weather.class);

        weatherService.create(testWeather1);

        verify(weatherEntityRepositoryJPA, times(1)).save(weatherArgumentCaptor.capture());

        assertThat(weatherArgumentCaptor.getValue()).isEqualTo(testWeather1);
    }

    @Test
    void cantCreateWeatherIfWeatherEntityExistShouldThrowException() {
        given(weatherEntityRepositoryJPA.existsById(testWeather1.getId())).willReturn(true);

        assertThatThrownBy(() -> weatherService.create(testWeather1)).isInstanceOf(EntityExistsException.class);
        verify(weatherEntityRepositoryJPA, never()).save(testWeather1);
    }

    @Test
    void cantCreateWeatherIfIsNotNullShouldThrowException() {
        testWeather1.setId(null);

        assertThatThrownBy(() -> weatherService.create(testWeather1)).isInstanceOf(EntityExistsException.class);

        verify(weatherEntityRepositoryJPA, never()).save(testWeather1);
    }

    @Test
    void canUpdateWeatherShouldReturnWeather() {
        given(weatherEntityRepositoryJPA.existsById(testWeather1.getId())).willReturn(true);

        weatherService.update(testWeather1.getId(), testWeather2);

        ArgumentCaptor<Weather> weatherArgumentCaptor = ArgumentCaptor.forClass(Weather.class);

        verify(weatherEntityRepositoryJPA, times(1)).save(weatherArgumentCaptor.capture());
        assertThat(weatherArgumentCaptor.getValue()).isEqualTo(testWeather2);
    }

    @Test
    void cantUpdateWeatherIfWeatherDontExistShouldThrowException() {
        given(weatherEntityRepositoryJPA.existsById(testWeather1.getId())).willReturn(false);

        assertThatThrownBy(() -> weatherService.update(testWeather1.getId(), testWeather2)).isInstanceOf(EntityNotFoundException.class);
        verify(weatherEntityRepositoryJPA, never()).save(any());
    }

    @Test
    void canDeleteWeatherShouldReturnWeather() {
        Long testId = testCity1.getId();

        weatherService.deleteById(testId);
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        verify(weatherEntityRepositoryJPA, times(1)).deleteById(argument.capture());
        assertThat(argument.getValue()).isEqualTo(testId);
    }

    @Test
    void canGetWeatherIfIdExistShouldReturnWeatherAndPutWeatherToCache() {
        Long testId = testWeather1.getId();
        given(weatherCache.get(testId)).willReturn(Optional.empty());
        given(weatherEntityRepositoryJPA.findById(testId)).willReturn(Optional.of(testWeather1));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        weatherService.findById(testId);

        verify(weatherCache, times(1)).get(argument.capture());

        assertThat(argument.getValue()).isEqualTo(testId);

        verify(weatherCache, times(1)).put(testId, testWeather1);
    }

    @Test
    void canGetWeatherWithExistentIdFromCacheShouldReturnWeatherFromCache() {
        Long testId = testWeather1.getId();
        given(weatherCache.get(testId)).willReturn(Optional.of(testWeather1));
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
        weatherService.findById(testId);

        verify(weatherCache, times(1)).get(argument.capture());
        verify(weatherEntityRepositoryJPA, times(0)).findById(argument.capture());

        assertThat(argument.getValue()).isEqualTo(testId);
    }

    @Test
    void canDeleteIfWeatherContainsInCacheShouldDeleteWeatherFromCache() {
        Long testId = testCity1.getId();
        weatherService.deleteById(testId);
        ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);

        verify(weatherCache, times(1)).delete(argument.capture());
        assertThat(argument.getValue()).isEqualTo(testId);
    }
}
