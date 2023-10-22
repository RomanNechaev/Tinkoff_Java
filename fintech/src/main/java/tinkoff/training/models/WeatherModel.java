package tinkoff.training.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class WeatherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "temperature")
    private double temperatureValue;
    private String date;
    private String time;

    public WeatherModel(String name, double temperatureValue, String date, String time) {
        this.name = name;
        this.temperatureValue = temperatureValue;
        this.date = date;
        this.time = time;
    }
}
