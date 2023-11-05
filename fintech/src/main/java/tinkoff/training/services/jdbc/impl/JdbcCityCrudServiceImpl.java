package tinkoff.training.services.jdbc.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tinkoff.training.entities.City;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.services.jdbc.JdbcCrudService;
import tinkoff.training.utils.exceptions.application.EntityExistsException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcCityCrudServiceImpl implements JdbcCrudService<City> {

    private final CrudRepository<City> cityRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Override
    public City create(City city) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        if (city.getId() != null) {
            cityRepository.findById(city.getId()).ifPresent(exception -> {
                throw new EntityExistsException("City Entity already exists!!!");
            });
        }
        City save = cityRepository.save(city);
        platformTransactionManager.commit(transaction);
        return save;
    }

    @Override
    public City update(Long id, City city) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        if (cityRepository.findById(id).isEmpty()) {
            throw new EntityExistsException("City entity not found");
        }
        City updated = cityRepository.update(city);
        platformTransactionManager.commit(transaction);
        return updated;
    }

    @Override
    public List<City> findAll() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        List<City> cities = Collections.unmodifiableList(cityRepository.findAll());
        platformTransactionManager.commit(transaction);
        return cities;
    }

    @Override
    public City findById(Long id) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        City city = cityRepository.findById(id).orElseThrow(() -> new EntityExistsException("City entity not found"));
        platformTransactionManager.commit(transaction);
        return city;
    }

    @Override
    public void deleteById(Long id) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        cityRepository.deleteById(id);
        platformTransactionManager.commit(transaction);
    }
}
