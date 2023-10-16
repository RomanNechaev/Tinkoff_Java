package tinkoff.training.services.spring_data_jpa;

import java.util.List;

public interface CrudService<T> {

    List<T> findAll();

    T findById(Long id);

    T create(T entity);

    T update(Long id, T entity);

    void deleteById(Long id);
}
