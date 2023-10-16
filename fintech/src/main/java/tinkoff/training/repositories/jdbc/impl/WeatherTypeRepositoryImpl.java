package tinkoff.training.repositories.jdbc.impl;

import org.springframework.stereotype.Repository;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import javax.sql.DataSource;
import java.sql.SQLException;

import static tinkoff.training.repositories.QueriesProviderImpl.*;

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
             var statement = connection.prepareStatement(UPDATE_WEATHER_TYPE_BY_ID)) {
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
             var statement = connection.prepareStatement(INSERT_WEATHER_TYPE)) {
            statement.setString(1, weatherType.getType());
            final var resultSet = statement.executeQuery();
            resultSet.next();
            createdWeatherType.setId(resultSet.getLong(ID));
            return createdWeatherType;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFindQQuery() {
        return FIND_WEATHER_TYPE_BY_ID;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_WEATHER_TYPE_BY_ID;
    }

    @Override
    public String getFindAllQuery() {
        return GET_ALL_FROM_WEATHER_DIRECTORY;
    }
}
