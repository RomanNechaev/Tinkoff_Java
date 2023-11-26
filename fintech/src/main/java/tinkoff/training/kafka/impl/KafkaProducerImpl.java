package tinkoff.training.kafka.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tinkoff.training.kafka.KafkaProducer;
import tinkoff.training.models.WeatherDto;

@Component
public class KafkaProducerImpl implements KafkaProducer {
    private final KafkaTemplate<String, WeatherDto> kafkaSender;
    private final String topic;

    public KafkaProducerImpl(KafkaTemplate<String, WeatherDto> kafkaSender, @Value("${spring.kafka.template.default-topic}") String topic) {
        this.kafkaSender = kafkaSender;
        this.topic = topic;

    }

    @Override
    public void produce(WeatherDto weatherDto) {
        kafkaSender.send(topic, weatherDto);
    }
}
