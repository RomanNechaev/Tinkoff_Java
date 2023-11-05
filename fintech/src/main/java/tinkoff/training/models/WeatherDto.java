package tinkoff.training.models;

import lombok.Data;

@Data
public class WeatherDto {
    private Long id;
    private Double temperature;
    private String date;
    private String time;
    private CityDto city;
    private WeatherTypeDto type;
}
