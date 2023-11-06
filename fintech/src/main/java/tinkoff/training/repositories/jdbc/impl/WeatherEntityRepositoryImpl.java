package tinkoff.training.repositories.jdbc.impl;

import org.springframework.stereotype.Repository;
import tinkoff.training.entities.Weather;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


@Repository
public class WeatherEntityRepositoryImpl extends CrudRepository<Weather> {
    public WeatherEntityRepositoryImpl(DataSource dataSource, RepositoryMapper<Weather> repositoryMapper) {
        super(dataSource, repositoryMapper);
    }

    @Override
    public Weather save(Weather entity) {
        if (entity.getId() == null) {
            return create(entity);
        } else return update(entity);
    }

    @Override
    public Weather update(Weather entity) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement("UPDATE WEATHER SET TEMPERATURE = ?, DATE = ?, TIME = ? WHERE ID = ?")) {
            statement.setDouble(1, entity.getTemperature());
            statement.setString(2, entity.getDate());
            statement.setString(3, entity.getTime());
            statement.setLong(4, entity.getId());
            statement.execute();
            return findById(entity.getId()).orElseThrow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Weather create(Weather entity) {
        Weather weather = new Weather(null, entity.getTemperature(), entity.getDate(), entity.getTime(), entity.getCity(), entity.getType());
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement("INSERT INTO WEATHER(TEMPERATURE,CITY_ID,WEATHER_TYPE_ID,DATE,TIME) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, entity.getTemperature());
            statement.setLong(2, entity.getType().getId());
            statement.setLong(3, entity.getCity().getId());
            statement.setString(4, entity.getDate());
            statement.setString(5, entity.getTime());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    weather.setId(generatedKeys.getLong(1));
                }
            }
            return weather;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFindQuery() {
        return "SELECT CITY.NAME AS CITY_NAME, WEATHER.ID, WEATHER.TEMPERATURE, WEATHER.CITY_ID," +
                " WEATHER.WEATHER_TYPE_ID,WEATHER.DATE,WEATHER.TIME, WEATHER_DIRECTORY.TYPE" +
                " FROM WEATHER JOIN CITY ON CITY.CITY_ID = WEATHER.CITY_ID JOIN WEATHER_DIRECTORY" +
                " ON WEATHER_DIRECTORY.WEATHER_TYPE_ID= WEATHER.WEATHER_TYPE_ID WHERE ID=?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM WEATHER WHERE ID = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT CITY.NAME AS CITY_NAME, WEATHER.ID, WEATHER.TEMPERATURE, WEATHER.CITY_ID," +
                " WEATHER.WEATHER_TYPE_ID,WEATHER.DATE,WEATHER.TIME, WEATHER_DIRECTORY.TYPE" +
                " FROM WEATHER JOIN CITY ON CITY.CITY_ID = WEATHER.CITY_ID JOIN WEATHER_DIRECTORY" +
                " ON WEATHER_DIRECTORY.WEATHER_TYPE_ID= WEATHER.WEATHER_TYPE_ID";
    }
}
