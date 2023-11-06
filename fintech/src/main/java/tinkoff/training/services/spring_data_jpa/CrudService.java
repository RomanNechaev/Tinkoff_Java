package tinkoff.training.services.spring_data_jpa;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CrudService<T> {
    @Transactional(isolation = Isolation.READ_COMMITTED)
    List<T> findAll();

    @Transactional(isolation = Isolation.READ_COMMITTED)
    T findById(Long id);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    T create(T entity);

    @Transactional(isolation = Isolation.SERIALIZABLE)
    T update(Long id, T entity);

    @Transactional(isolation = Isolation.READ_COMMITTED)
    void deleteById(Long id);

    /**
     * Поиск сущности по имени, в случае, если сущности нет, создаем новую сущностьи сохраняем в БД
     *
     * @param name имя сущности
     * @return сущность
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.NESTED)
    T findByName(String name);
}
