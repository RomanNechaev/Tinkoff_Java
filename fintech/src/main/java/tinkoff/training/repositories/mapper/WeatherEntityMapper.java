package tinkoff.training.repositories.mapper;

import org.springframework.stereotype.Component;
import tinkoff.training.entities.City;
import tinkoff.training.entities.Weather;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static tinkoff.training.repositories.QueriesProviderImpl.*;

@Component
public class WeatherEntityMapper extends RepositoryMapper<Weather> {
    @Override
    public Weather convertToEntity(ResultSet resultSet) {
        try {
            return new Weather(resultSet.getLong("ID"),
                    resultSet.getDouble("TEMPERATURE"),
                    resultSet.getString("DATE"),
                    resultSet.getString("TIME"),
                    (City) resultSet.getObject("CITY_ID"),
                    (WeatherType) resultSet.getObject("WEATHER_TYPE_ID"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Weather> convertToListEntity(ResultSet resultSet) {
        LinkedList<Weather> result = new LinkedList<>();
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
