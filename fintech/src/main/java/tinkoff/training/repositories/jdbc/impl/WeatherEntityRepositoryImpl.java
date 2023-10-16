package tinkoff.training.repositories.jdbc.impl;

import org.springframework.stereotype.Repository;
import tinkoff.training.entities.WeatherEntity;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import javax.sql.DataSource;
import java.sql.SQLException;

import static tinkoff.training.repositories.QueriesProviderImpl.*;

@Repository
public class WeatherEntityRepositoryImpl extends CrudRepository<WeatherEntity> {
    public WeatherEntityRepositoryImpl(DataSource dataSource, RepositoryMapper<WeatherEntity> repositoryMapper) {
        super(dataSource, repositoryMapper);
    }

    @Override
    public WeatherEntity save(WeatherEntity entity) {
        if (entity.getId() == null) {
            return create(entity);
        } else return update(entity);
    }

    @Override
    public WeatherEntity update(WeatherEntity entity) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(UPDATE_WEATHER_BY_ID)) {
            statement.setLong(1, entity.getId());
            statement.setDouble(2, entity.getTemperature());
            statement.setObject(3, entity.getType());
            statement.execute();
            return findById(entity.getId()).orElseThrow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public WeatherEntity create(WeatherEntity entity) {
        WeatherEntity weatherEntity = new WeatherEntity(null, entity.getTemperature(), entity.getType());
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(INSERT_WEATHER)) {
            statement.setDouble(1, entity.getTemperature());
            statement.setObject(2, entity.getType());
            final var resultSet = statement.executeQuery();
            resultSet.next();
            weatherEntity.setId(resultSet.getLong(ID));
            return weatherEntity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFindQQuery() {
        return FIND_WEATHER_BY_ID;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_WEATHER_BY_ID;
    }

    @Override
    public String getFindAllQuery() {
        return GET_ALL_WEATHER;
    }
}
