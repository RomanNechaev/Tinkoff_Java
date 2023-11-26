package tinkoff.training.kafka;

import tinkoff.training.models.WeatherDto;

public interface KafkaProducer {
    void produce(WeatherDto weatherDto);
}
