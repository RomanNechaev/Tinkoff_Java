package tinkoff.training.kafka.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tinkoff.training.kafka.KafkaConsumer;
import tinkoff.training.models.WeatherDto;
import tinkoff.training.services.MovingAverage;


@Component
public class KafkaConsumerImpl implements KafkaConsumer {
    private final MovingAverage movingAverage;

    public KafkaConsumerImpl() {
        movingAverage = new MovingAverage(3);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void consume(WeatherDto weatherDto) {
        movingAverage.getAverageSum(weatherDto.getTemperature())
                .ifPresentOrElse(
                        average -> System.out.println("Скользящее среднее: " + average),
                        () -> System.out.println("Окно еще не заполнилось")
                );
    }
}
