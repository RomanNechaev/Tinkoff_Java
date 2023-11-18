package tinkoff.training;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.mappers.WeatherMapper;
import tinkoff.training.repositories.spring_data_jpa.CityRepositoryJPA;
import tinkoff.training.repositories.spring_data_jpa.WeatherEntityRepositoryJPA;
import tinkoff.training.repositories.spring_data_jpa.WeatherTypeRepositoryJPA;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationWeatherEntityControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private WeatherEntityRepositoryJPA weatherEntityRepositoryJPA;
    @Autowired
    private CityRepositoryJPA cityRepositoryJPA;
    @Autowired
    private WeatherTypeRepositoryJPA weatherTypeRepositoryJPA;
    private Weather testWeather;
    private City testCity;
    private WeatherType testWeatherType;
    @Autowired
    private WeatherMapper weatherMapper;

    @BeforeEach
    void setUp() {
        weatherEntityRepositoryJPA.deleteAll();
        cityRepositoryJPA.deleteAll();
        weatherEntityRepositoryJPA.deleteAll();
        testCity = new City(10L, "Berlin");
        testWeatherType = new WeatherType(10L, "cloudy");
        testWeather = new Weather();
        testWeather.setId(10L);
        testWeather.setTemperature(12.4);
        testWeather.setDate("2023-04-19");
        testWeather.setTime("14:03:00");
        testWeather.setType(testWeatherType);
        testWeather.setCity(testCity);
        cityRepositoryJPA.save(testCity);
        weatherTypeRepositoryJPA.save(testWeatherType);
    }

    @AfterEach
    void tearDown() {
        weatherEntityRepositoryJPA.deleteAll();
    }

    @Test
    @WithAnonymousUser
    void canGetWeatherListWithoutAuthenticationShouldBeDenied() throws Exception {
        var requestBuilder = get("/api/repository/jpa/weather");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void canGetWeathersListWithUserRoleShouldReturnResponseWithStatusOk() throws Exception {
        var saved = weatherEntityRepositoryJPA.save(testWeather);
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        weatherEntityRepositoryJPA.findAll().forEach(x -> System.out.println(x.getCity().getName()));
        var requestBuilder = get("/api/repository/jpa/weather");
        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].id", is(Integer.parseInt(Long.toString(saved.getId())))),
                        jsonPath("$[0].city.name", is(testCity.getName())),
                        jsonPath("$[0].type.type", is(testWeatherType.getType())),
                        jsonPath("$[0].temperature", is(testWeather.getTemperature())),
                        jsonPath("$[0].date", is(testWeather.getDate())),
                        jsonPath("$[0].time", is(testWeather.getTime())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void canGetWeathersListWithAdminRoleShouldReturnResponseWithStatusOk() throws Exception {
        var saved = weatherEntityRepositoryJPA.save(testWeather);
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        weatherEntityRepositoryJPA.findAll().forEach(x -> System.out.println(x.getCity().getName()));
        var requestBuilder = get("/api/repository/jpa/weather");
        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].id", is(Integer.parseInt(Long.toString(saved.getId())))),
                        jsonPath("$[0].city.name", is(testCity.getName())),
                        jsonPath("$[0].type.type", is(testWeatherType.getType())),
                        jsonPath("$[0].temperature", is(testWeather.getTemperature())),
                        jsonPath("$[0].date", is(testWeather.getDate())),
                        jsonPath("$[0].time", is(testWeather.getTime())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void canDeleteWeathersWithRegularUserShouldReturnEmptyResponseWithStatusNoContent() throws Exception {
        weatherEntityRepositoryJPA.save(testWeather);
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        System.out.println(testWeather.getId());
        var requestBuilder = delete("/api/repository/jpa/weather/" + testWeather.getId());
        mockMvc.perform(requestBuilder)

                .andExpectAll(
                        status().isNoContent());
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(0);

    }

    @Test
    @WithMockUser
    void canDeleteWeathersWithRegularUserShouldBeForbidden() throws Exception {
        weatherEntityRepositoryJPA.save(testWeather);
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        var requestBuilder = delete("/api/repository/jpa/weather/" + testWeather.getId());
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isForbidden());
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
    }

    @Test
    @WithMockUser
    void canCreateWeathersWithRegularUserShouldBeForbidden() throws Exception {
        String jsonRequest = new ObjectMapper().writeValueAsString(weatherMapper.toDTO(testWeather));
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(0);
        var requestBuilder = post("/api/repository/jpa/weather/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);
        ;
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isForbidden());
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(0);
    }

    @Test
    @WithMockUser
    void canUpdateWeathersWithRegularUserShouldBeForbidden() throws Exception {
        weatherEntityRepositoryJPA.save(testWeather);
        String jsonRequest = new ObjectMapper().writeValueAsString(weatherMapper.toDTO(testWeather));
        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        var requestBuilder = put("/api/repository/jpa/weather/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest);
        ;
        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isForbidden());
    }
}
