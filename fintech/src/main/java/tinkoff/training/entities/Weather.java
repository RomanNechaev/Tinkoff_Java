package tinkoff.training.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperature;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;

    @ManyToOne()
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne()
    @JoinColumn(name = "weather_type_id")
    private WeatherType type;

    public Weather(Double temperature, String lastDateUpdated, String lastTimeUpdated, City city, WeatherType weatherType) {
        this.temperature = temperature;
        this.date = lastDateUpdated;
        this.time = lastTimeUpdated;
        this.city = city;
        this.type = weatherType;
    }
}
