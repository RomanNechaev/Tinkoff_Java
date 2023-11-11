package tinkoff.training;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;
import tinkoff.training.client.WeatherApiClient;
import tinkoff.training.config.WeatherClientProperties;
import tinkoff.training.entities.City;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.models.WeatherApiResponse;
import tinkoff.training.repositories.spring_data_jpa.CityRepositoryJPA;
import tinkoff.training.repositories.spring_data_jpa.WeatherEntityRepositoryJPA;
import tinkoff.training.repositories.spring_data_jpa.WeatherTypeRepositoryJPA;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AutoConfigureMockMvc
@SpringBootTest
public class WeatherApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    @SpyBean
    private WeatherApiClient weatherApiClient;
    @SpyBean
    private WeatherClientProperties weatherClientProperties;

    @Autowired
    private CityRepositoryJPA cityRepositoryJPA;

    @Autowired
    private WeatherTypeRepositoryJPA weatherTypeRepositoryJPA;

    @Autowired
    private WeatherEntityRepositoryJPA weatherEntityRepositoryJPA;

    private static final String CITY_NAME = "Ekaterinburg";
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();
    private static final String WEATHER_TYPE = "Sunny";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private WeatherApiResponse weatherApiResponse;

    @BeforeEach
    void setUp() {

        weatherEntityRepositoryJPA.deleteAll();
        weatherTypeRepositoryJPA.deleteAll();
        cityRepositoryJPA.deleteAll();
        WeatherApiResponse.Current.Condition condition = WeatherApiResponse
                .Current
                .Condition
                .builder()
                .weatherType("Rainy").build();

        WeatherApiResponse.Current current = WeatherApiResponse.Current.builder()
                .lastUpdated(DATE_TIME_FORMATTER.format(LOCAL_DATE_TIME))
                .condition(condition)
                .temperatureCelsius(5.2)
                .build();

        WeatherApiResponse.Location location = WeatherApiResponse.Location
                .builder()
                .name(CITY_NAME)
                .build();

        weatherApiResponse = WeatherApiResponse
                .builder()
                .current(current)
                .location(location)
                .build();
    }


    @AfterEach
    void tearDown() {
        weatherEntityRepositoryJPA.deleteAll();
        weatherTypeRepositoryJPA.deleteAll();
        cityRepositoryJPA.deleteAll();
    }

    @Test
    @WithMockUser
    void canGetCurrentWeatherInfoIfTypeAndCityNotExistsShouldReturnCurrentWeatherWithSaveEntities() throws Exception {
        given(weatherApiClient.getCurrentWeather(CITY_NAME))
                .willReturn(Mono.just(weatherApiResponse));

        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(0);
        assertThat(weatherTypeRepositoryJPA.findAll()).hasSize(0);
        assertThat(cityRepositoryJPA.findAll()).hasSize(0);

        String currentDate = weatherApiResponse.getCurrent().getLastUpdated().split(" ")[0];

        var request = get("/api/weather/" + CITY_NAME + "/info");

        mockMvc.perform(request)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.city.name", is(weatherApiResponse.getLocation().getName())),
                        jsonPath("$.date", is(currentDate)),
                        jsonPath("$.temperature", is(weatherApiResponse.getCurrent().getTemperatureCelsius())),
                        jsonPath("$.type.type", is(weatherApiResponse.getCurrent().getCondition().getWeatherType())));

        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        assertThat(weatherTypeRepositoryJPA.findAll()).hasSize(1);
        assertThat(cityRepositoryJPA.findAll()).hasSize(1);
    }

    @Test
    @WithMockUser
    void canGetCurrentWeatherInfoIfTypeAndCityExistsShouldReturnCurrentWeatherWithoutSaveEntities() throws Exception {
        given(weatherApiClient.getCurrentWeather(CITY_NAME))
                .willReturn(Mono.just(weatherApiResponse));

        WeatherType weatherType = new WeatherType();
        weatherType.setType(WEATHER_TYPE);

        City city = new City();
        city.setName(CITY_NAME);

        weatherTypeRepositoryJPA.save(weatherType);
        cityRepositoryJPA.save(city);

        assertThat(cityRepositoryJPA.findAll()).hasSize(1);

        String currentDate = weatherApiResponse.getCurrent().getLastUpdated().split(" ")[0];

        var request = get("/api/weather/" + CITY_NAME + "/info");

        mockMvc.perform(request)
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.city.name", is(weatherApiResponse.getLocation().getName())),
                        jsonPath("$.date", is(currentDate)),
                        jsonPath("$.temperature", is(weatherApiResponse.getCurrent().getTemperatureCelsius())),
                        jsonPath("$.type.type", is(weatherApiResponse.getCurrent().getCondition().getWeatherType())));

        assertThat(weatherEntityRepositoryJPA.findAll()).hasSize(1);
        assertThat(cityRepositoryJPA.findAll()).hasSize(1);
    }

    @Test
    @WithMockUser
    void canGegCurrentWeatherWithoutExistentLocationShouldReturnResponseWithStatusBadRequest() throws Exception {

        var request = get("/api/weather/" + "123" + "/info");

        mockMvc.perform(request)
                .andExpectAll(status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", is("No matching location found.")));
    }

    @Test
    @WithMockUser
    void canGegCurrentWeatherWithoutParameterQShouldReturnResponseWithStatusBadRequest() throws Exception {
        given(weatherClientProperties.getCurrentWeatherUri()).willReturn("/current.json?={city}&key={key}");
        var request = get("/api/weather/" + CITY_NAME + "/info");
        mockMvc.perform(request)
                .andExpectAll(status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", is("Parameter q is missing.")));
    }

    @Test
    @WithMockUser
    void canGegCurrentWeatherWithInvalidUrlShouldReturnResponseWithStatusBadRequest() throws Exception {
        given(weatherClientProperties.getCurrentWeatherUri()).willReturn("/curredfnt.json?={city}&key={key}");
        var request = get("/api/weather/" + CITY_NAME + "/info");

        mockMvc.perform(request)
                .andExpectAll(status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", is("API URL is invalid.")));
    }

    @Test
    @WithMockUser
    void canGegCurrentWeatherWithoutKeyShouldReturnResponseWithStatusServerError() throws Exception {
        given(weatherClientProperties.getCurrentWeatherUri()).willReturn("/current.json?={city}&key=");
        var request = get("/api/weather/" + CITY_NAME + "/info");

        mockMvc.perform(request)
                .andExpectAll(status().is5xxServerError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", is("Something wrong... API key is invalid or not provided.")));
    }

    @Test
    @WithMockUser
    void canGegCurrentWeatherWithInvalidKeyShouldReturnResponseWithStatusServerError() throws Exception {
        given(weatherClientProperties.getCurrentWeatherUri()).willReturn("/current.json?={city}&key={key}kdsfk32");
        var request = get("/api/weather/" + CITY_NAME + "/info");

        mockMvc.perform(request)
                .andExpectAll(status().is5xxServerError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message", is("Something wrong... API key has been disabled.")));
    }

}
