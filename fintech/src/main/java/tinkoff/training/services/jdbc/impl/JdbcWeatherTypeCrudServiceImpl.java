package tinkoff.training.services.jdbc.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import tinkoff.training.entities.WeatherType;
import tinkoff.training.repositories.jdbc.CrudRepository;
import tinkoff.training.services.jdbc.JdbcCrudService;
import tinkoff.training.utils.exceptions.application.EntityNotFoundException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcWeatherTypeCrudServiceImpl implements JdbcCrudService<WeatherType> {

    private final CrudRepository<WeatherType> weatherTypeRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Override
    public WeatherType create(WeatherType weatherType) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        if (weatherType.getId() != null) {
            if (weatherTypeRepository.findById(weatherType.getId()).isPresent()) {
                throw new EntityNotFoundException("Weather type already exists!");
            }
        }
        WeatherType saved = weatherTypeRepository.save(weatherType);
        platformTransactionManager.commit(transaction);
        return saved;
    }

    @Override
    public WeatherType update(Long id, WeatherType weatherType) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        if (weatherTypeRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Weather Type not found!");
        }
        WeatherType saved = weatherTypeRepository.save(weatherType);
        platformTransactionManager.commit(transaction);
        return saved;
    }

    @Override
    public List<WeatherType> findAll() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        List<WeatherType> weatherTypes = Collections.unmodifiableList(weatherTypeRepository.findAll());
        platformTransactionManager.commit(transaction);
        return weatherTypes;
    }

    @Override
    public WeatherType findById(Long id) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        WeatherType weatherType = weatherTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Weather Type not found!"));
        platformTransactionManager.commit(transaction);
        return weatherType;
    }

    @Override
    public void deleteById(Long id) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        weatherTypeRepository.deleteById(id);
        platformTransactionManager.commit(transaction);

    }
}
