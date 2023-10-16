package tinkoff.training.repositories.jdbc;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class CrudRepository<T> implements QueriesProvider {
    protected final DataSource dataSource;
    protected final RepositoryMapper<T> repositoryMapper;

    protected CrudRepository(DataSource dataSource, RepositoryMapper<T> repositoryMapper) {
        this.dataSource = dataSource;
        this.repositoryMapper = repositoryMapper;
    }

    public Optional<T> findById(Long id) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getFindQQuery())) {
            statement.setLong(1, id);
            final var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(repositoryMapper.convertToEntity(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> findAll() {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getFindAllQuery())) {
            final var resultSet = statement.executeQuery();
            return repositoryMapper.convertToListEntity(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(Long id) {
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(getDeleteQuery())) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    abstract public T save(T entity);

    abstract public T update(T entity);
}
