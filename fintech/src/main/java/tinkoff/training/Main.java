package tinkoff.training;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Weather> weatherList = List.of(
                new Weather("Свердловская область", 19, "12.05.2019", "12:43:32"),
                new Weather("Пермский край", 14.3, "21.09.2020", "09:34:02"),
                new Weather("Москва", -5.8, "13.12.2018", "16:08:12"),
                new Weather("Чеченская Республика", 28, "18.07.2021", "15:03:45"),
                new Weather("Челябинская область", -12, "19.01.2008", "13:09:09"),
                new Weather("Республика Алтай", 14.4, "21.05.2013", "18:18:01"),
                new Weather("Санкт-Петербург", 4.2, "03.04.2007", "15:59:32"),
                new Weather("Санкт-Петербург", 28, "03.08.2009", "17:59:32"));

        WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer();

        System.out.println(weatherAnalyzer.getAverageTemperature(weatherList));
        System.out.println(weatherAnalyzer.findRegionsAboveTemperature(weatherList, 3));
        System.out.println(weatherAnalyzer.idToTemperature(weatherList));
        System.out.println(weatherAnalyzer.toSameWeathereMap(weatherList));
    }
}
