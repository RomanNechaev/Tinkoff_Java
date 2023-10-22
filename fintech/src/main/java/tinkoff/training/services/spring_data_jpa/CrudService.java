package tinkoff.training.services.spring_data_jpa;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CrudService<T> {

    List<T> findAll();

    T findById(Long id);

    T create(T entity);

    T update(Long id, T entity);

    void deleteById(Long id);

    /**
     * Поиск сущности по имени, в случае, если сущности нет, создаем новую сущностьи сохраняем в БД
     *
     * @param name имя сущности
     * @return сущность
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    T findByName(String name);
}
