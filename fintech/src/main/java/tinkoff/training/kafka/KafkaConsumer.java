package tinkoff.training.kafka;

import tinkoff.training.models.WeatherDto;

public interface KafkaConsumer {
    void consume(WeatherDto weatherDto);
}
