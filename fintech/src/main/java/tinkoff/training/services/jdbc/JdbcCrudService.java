package tinkoff.training.services.jdbc;

import java.util.List;

public interface JdbcCrudService<T> {

    T create(T entity);

    T update(Long id, T entity);

    List<T> findAll();

    T findById(Long id);

    void deleteById(Long id);
}
