package tinkoff.training.repositories.jdbc;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public abstract class RepositoryMapper<O> {
    abstract public O convertToEntity(ResultSet resultSet);

    abstract public List<O> convertToListEntity(ResultSet resultSet);
}
