package tinkoff.training.repositories.jdbc.impl;

import org.springframework.stereotype.Repository;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import javax.sql.DataSource;
import java.sql.SQLException;


@Repository
public class WeatherTypeRepositoryImpl extends CrudRepository<WeatherType> {
    public WeatherTypeRepositoryImpl(DataSource dataSource, RepositoryMapper<WeatherType> repositoryMapper) {
        super(dataSource, repositoryMapper);
    }

    @Override
    public WeatherType save(WeatherType entity) {
        if (entity.getId() == null) {
            return create(entity);
        } else return update(entity);
    }

    @Override
    public WeatherType update(WeatherType entity) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement( "UPDATE WEATHER_DIRECTORY SET TYPE = ? WHERE ID = ?")) {
            statement.setString(1, entity.getType());
            statement.execute();
            return findById(entity.getId()).orElseThrow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public WeatherType create(WeatherType weatherType) {
        WeatherType createdWeatherType = new WeatherType(null, weatherType.getType());
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement("INSERT INTO WEATHER_DIRECTORY(TYPE) VALUES (?)")) {
            statement.setString(1, weatherType.getType());
            final var resultSet = statement.executeQuery();
            resultSet.next();
            createdWeatherType.setId(resultSet.getLong("ID"));
            return createdWeatherType;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM WEATHER_DIRECTORY FIND_WEATHER_TYPE_BY_ID";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM WEATHER_DIRECTORY WHERE ID = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "SELECT * FROM WEATHER_DIRECTORY";
    }
}
