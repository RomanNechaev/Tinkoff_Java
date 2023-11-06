package tinkoff.training.repositories.mapper;

import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


@Component
public class CityMapper extends RepositoryMapper<City> {

    @Override
    public City convertToEntity(ResultSet resultSet) {
        try {
            return new City(resultSet.getLong("CITY_ID"), resultSet.getString("NAME"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<City> convertToListEntity(ResultSet resultSet) {
        LinkedList<City> result = new LinkedList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            result.add(convertToEntity(resultSet));
        }
        return result;
    }
}
