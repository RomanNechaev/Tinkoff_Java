package tinkoff.training.repositories.jdbc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tinkoff.training.entities.City;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import javax.sql.DataSource;
import java.sql.SQLException;
@Repository
public class CityRepositoryImpl extends CrudRepository<City> {

    @Autowired
    public CityRepositoryImpl(DataSource dataSource, RepositoryMapper<City> repositoryMapper) {
        super(dataSource, repositoryMapper);
    }

    @Override
    public City save(City entity) {
        if (entity.getId() == null) {
            return create(entity);
        } else return update(entity);
    }


    public City create(City entity) {
        City createdCity = new City(null, entity.getName());
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement("INSERT INTO CITY(NAME) VALUES(?)")) {
            statement.setString(1, entity.getName());
            final var resultSet = statement.executeQuery();
            resultSet.next();
            createdCity.setId(resultSet.getLong("ID"));
            return createdCity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public City update(City entity) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement("UPDATE CITY SET NAME WHERE ID = ?")) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.execute();
            return findById(entity.getId()).orElseThrow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFindQuery() {
        return "SELECT * FROM CITY WHERE CITY.ID = ?";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM CITY WHERE ID = ?";
    }

    @Override
    public String getFindAllQuery() {
        return "ELECT * FROM CITY";
    }
}
