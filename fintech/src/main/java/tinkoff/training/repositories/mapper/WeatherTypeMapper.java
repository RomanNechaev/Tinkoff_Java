package tinkoff.training.repositories.mapper;

import org.springframework.stereotype.Component;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.jdbc.RepositoryMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Component
public class WeatherTypeMapper extends RepositoryMapper<WeatherType> {
    @Override
    public WeatherType convertToEntity(ResultSet resultSet) {
        try {
            return new WeatherType(resultSet.getLong("ID"), resultSet.getString("TYPE"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeatherType> convertToListEntity(ResultSet resultSet) {
        LinkedList<WeatherType> result = new LinkedList<>();
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
