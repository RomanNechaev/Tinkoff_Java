package tinkoff.training.controllers.spring_data_jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.repositories.spring_data_jpa.WeatherEntityRepositoryJPA;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@SpringBootTest
class WeatherEntityControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private WeatherEntityRepositoryJPA weatherEntityRepositoryJPA;
    @Autowired
    private WeatherMapper weatherMapper;


    private WeatherDto testWeather1;
    private WeatherDto testWeather2;

    private City testCity1;
    private City testCity2;

    private WeatherType testWeatherType1;
    private WeatherType testWeatherType2;

    @BeforeEach
    void setUp() {
        weatherEntityRepositoryJPA.deleteAll();
        testCity1 = new City(1L, "Perm");
        testCity2 = new City(2L, "Grozny");
        testWeatherType1 = new WeatherType(1L, "cloudy");
        testWeatherType2 = new WeatherType(2L, "rainy");
        testWeather1 = new WeatherDto();
        testWeather2 = new WeatherDto();
        testWeather1.setId(1L);
        testWeather1.setTemperature(12.4);
        testWeather1.setDate("2023-04-19");
        testWeather1.setTime("14:03:00");
        testWeather1.setCity(testCity1.getName());
        testWeather1.setType(testWeatherType1.getType());

        testWeather2.setId(2L);
        testWeather2.setTemperature(24.4);
        testWeather2.setDate("2023-08-18");
        testWeather2.setTime("19:03:00");
        testWeather2.setCity(testCity2.getName());
        testWeather2.setType(testWeather2.getType());
    }

    @AfterEach
    void tearDown() {
        weatherEntityRepositoryJPA.deleteAll();
    }

    @Test
    void canGetWeatherShouldReturnResponseWithStatusOk() throws Exception {

        Weather saved = weatherEntityRepositoryJPA.save(weatherMapper.toWeather(testWeather1));
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        var requestBuilder = get("/repository/jpa/weather");
        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].id", is(Integer.parseInt(Long.toString(saved.getId())))),
                        jsonPath("$[0].city", is(testCity1.getName())),
                        jsonPath("$[0].type", is(testWeatherType1.getType())),
                        jsonPath("$[0].temperature", is(testWeather1.getTemperature())),
                        jsonPath("$[0].date", is(testWeather1.getDate())),
                        jsonPath("$[0].time", is(testWeather1.getTime())));
    }

    @Test
    void canGetByIdShouldReturnResponseWeatherWithStatusOk() throws Exception {
        Weather saved = weatherEntityRepositoryJPA.save(weatherMapper.toWeather(testWeather1));
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        ;
        var requestBuilder = get("/repository/jpa/weather" + "/{id}", saved.getId());

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", is(Integer.parseInt(Long.toString(saved.getId())))),
                        jsonPath("$.city", is(testCity1.getName())),
                        jsonPath("$.type", is(testWeatherType1.getType())),
                        jsonPath("$.temperature", is(testWeather1.getTemperature())),
                        jsonPath("$.date", is(testWeather1.getDate())),
                        jsonPath("$.time", is(testWeather1.getTime())));
    }

    @Test
    void cantGetByIdWithNonExistentIdShouldReturnResponseWithStatusNotFound() throws Exception {
        weatherEntityRepositoryJPA.save(weatherMapper.toWeather(testWeather1));
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        var requestBuilder = get("/repository/jpa/weather" + "/{id}", 123L);
        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", is(
                                "Weather with input id not found!"
                        ))
                );
    }

    @Test
    void canCreateWeatherShouldReturnEmptyResponseWithStatusOk() throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(testWeather1);
        var requestBuilder = post("/repository/jpa/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isOk()
                );
    }

    @Test
    void cantCreateExistentWeatherShouldReturnResponseWithStatusBadRequest() throws Exception {
        var saved = weatherEntityRepositoryJPA.save(weatherMapper.toWeather(testWeather1));
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        testWeather1.setId(saved.getId());
        String jsonRequest = new ObjectMapper().writeValueAsString(testWeather1);
        var requestBuilder = post("/repository/jpa/weather")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);

        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", is(
                                "Weather already exists!"
                        ))
                );

    }

}