package tinkoff.training.services.jdbc;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JdbcCrudService<T> {
    @Transactional
    T create(T entity);

    T update(Long id, T entity);

    List<T> findAll();

    T findById(Long id);

    void deleteById(Long id);
}
