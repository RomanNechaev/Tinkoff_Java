package tinkoff.training.utils.cache;

import java.util.Optional;

public interface Cacheable<E,T> {

    Optional<T> get(E key);
    void put(E key, T value);
}