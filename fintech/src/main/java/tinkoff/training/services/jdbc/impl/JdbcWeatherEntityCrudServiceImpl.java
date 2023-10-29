package tinkoff.training.services.jdbc.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tinkoff.training.entities.Weather;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.services.jdbc.JdbcCrudService;
import tinkoff.training.utils.exceptions.application.EntityExistsException;
import tinkoff.training.utils.exceptions.application.EntityNotFoundException;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JdbcWeatherEntityCrudServiceImpl implements JdbcCrudService<Weather> {

    private final CrudRepository<Weather> weatherEntityCrudRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Override
    public Weather create(Weather weather) {
        weatherEntityCrudRepository.findById(weather.getId()).ifPresent(exception -> {
            throw new EntityExistsException("Weather entity already exists!");
        });
        return weatherEntityCrudRepository.save(weather);
    }

    @Override
    public Weather update(Long id, Weather weather) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        if (weatherEntityCrudRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Weather not found!");
        }
        Weather entity = weatherEntityCrudRepository.save(weather);
        platformTransactionManager.commit(transaction);
        return entity;
    }

    @Override
    public List<Weather> findAll() {
        return Collections.unmodifiableList(weatherEntityCrudRepository.findAll());
    }

    @Override
    public Weather findById(Long id) {
        return weatherEntityCrudRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Weather not found!"));
    }

    @Override
    public void deleteById(Long id) {
        weatherEntityCrudRepository.deleteById(id);
    }
}
