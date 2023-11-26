package tinkoff.training.utils.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tinkoff.training.entities.Weather;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class WeatherCache implements Cacheable<Long, Weather> {
    @Value("${cache.capacity}")
    private int capacity;
    private final Map<Long, Weather> map;
    private final ReentrantLock lock = new ReentrantLock(true);

    public WeatherCache() {
        this.map = new LinkedHashMap<>(
                capacity,
                0.75f,
                true
        ) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry<Long, Weather> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public Optional<Weather> get(Long id) {
        lock.lock();
        try {
            var value = map.get(id);
            return Optional.ofNullable(value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(Long id, Weather weather) {
        lock.lock();
        try {
            map.put(id, weather);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        map.clear();
    }

    public void delete(Long id) {
        lock.lock();
        try {
            if (map.containsKey(id)) {
                map.remove(id);
            }
        } finally {
            lock.unlock();
        }
    }

}
