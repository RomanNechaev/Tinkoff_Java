package tinkoff.training;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Класс для хранения информации о погоде в конкретном регионе
 */
@Getter
public class Weather {
    private final UUID id;
    private final String name;
    private final double temperatureValue;
    private final LocalDate date;
    private final LocalTime time;

    public Weather(String name, double temperatureValue, String date, String time) {
        this.id = RegionsID.regionsId.get(name);
        this.name = name;
        this.temperatureValue = temperatureValue;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.time = LocalTime.parse(time);
    }
}
