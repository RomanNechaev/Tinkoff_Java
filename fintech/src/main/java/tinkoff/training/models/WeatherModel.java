package tinkoff.training.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class WeatherModel {
    private Long id;
    private String name;
    private double temperatureValue;
    private String date;
    private String time;
    private String weatherType;

    public WeatherModel(String name, double temperatureValue, String date, String time, String weatherType) {
        this.name = name;
        this.temperatureValue = temperatureValue;
        this.date = date;
        this.time = time;
        this.weatherType = weatherType;
    }
}
