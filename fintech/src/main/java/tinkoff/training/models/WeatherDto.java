package tinkoff.training.models;
import lombok.Data;
import tinkoff.training.entities.City;
import tinkoff.training.entities.WeatherType;
@Data
public class WeatherDto {
    private Long id;
    private Double temperature;
    private String date;
    private String time;
    private String city;
    private String type;
}
