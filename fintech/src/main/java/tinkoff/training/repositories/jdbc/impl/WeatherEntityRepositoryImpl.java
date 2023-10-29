package tinkoff.training.repositories.jdbc.impl;

import org.springframework.stereotype.Repository;
import tinkoff.training.entities.Weather;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import javax.sql.DataSource;
import java.sql.SQLException;


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
             var statement = connection.prepareStatement("UPDATE WEATHER SET TEMPERATURE = ?, CITY_ID = ?, WEATHER_TYPE_ID = ? WHERE ID = ?")) {
            statement.setLong(1, entity.getId());
            statement.setDouble(2, entity.getTemperature());
            statement.setObject(3, entity.getType());
            statement.execute();
            return findById(entity.getId()).orElseThrow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Weather create(Weather entity) {
        Weather weather = new Weather(null, entity.getTemperature(), entity.getDate(), entity.getTime(), entity.getCity(), entity.getType());
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement( "INSERT INTO WEATHER(TEMPERATURE,CITY_ID,WEATHER_TYPE_ID) VALUES(?,?,?)")) {
            statement.setDouble(1, entity.getTemperature());
            statement.setObject(2, entity.getType());
            final var resultSet = statement.executeQuery();
            resultSet.next();
            weather.setId(resultSet.getLong("ID"));
            return weather;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFindQuery() {
        return " SELECT * FROM WEATHER WHERE WEATHER.ID = ?";
    }

    @Override
    public String getDeleteQuery() {
        return  "DELETE FROM WEATHER WHERE ID = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM WEATHER";
    }
}
