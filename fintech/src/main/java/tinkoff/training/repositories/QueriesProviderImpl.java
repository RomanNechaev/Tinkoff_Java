package tinkoff.training.repositories;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QueriesProviderImpl {
    public static final String CITY_ID = "CITY_ID";
    public static final String NAME = "NAME";
    public static final String WEATHER_TYPE = "WEATHER_TYPE";
    public static final String TEMPERATURE = "TEMPERATURE";
    public static final String TYPE = "TYPE";
    public static String ID = "ID";


    public static final String GET_ALL_CITY = """
            SELECT *
            FROM CITY
            """;
    public static final String GET_ALL_FROM_WEATHER_DIRECTORY = """
            SELECT *
            FROM WEATHER_DIRECTORY
            """;
    public static final String GET_ALL_WEATHER = """
            SELECT *
            FROM WEATHER
            """;

    public static final String FIND_CITY_BY_ID = """
            %s
            WHERE CITY.ID = ?
            """.formatted(GET_ALL_CITY);

    public static final String FIND_WEATHER_BY_ID = """
            %s
            WHERE WEATHER.ID = ?
            """.formatted(GET_ALL_WEATHER);

    public static final String FIND_WEATHER_TYPE_BY_ID = """
            %s
            WHERE WEATHER_DIRECTORY.ID = ?
            """.formatted(GET_ALL_FROM_WEATHER_DIRECTORY);

    public static final String DELETE_CITY_BY_ID = """
            DELETE
            FROM CITY
            WHERE ID = ?
            """;

    public static final String DELETE_WEATHER_BY_ID = """
            DELETE
            FROM WEATHER
            WHERE ID = ?
            """;

    public static final String DELETE_WEATHER_TYPE_BY_ID = """
            DELETE
            FROM WEATHER_DIRECTORY
            WHERE ID = ?
            """;

    public static final String INSERT_CITY = """
            INSERT INTO CITY(NAME)
            VALUES(?)
            """;

    public static final String INSERT_WEATHER = """
            INSERT INTO WEATHER(TEMPERATURE,CITY_ID,WEATHER_TYPE_ID)
            VALUES(?,?,?)
            """;
    public static final String INSERT_WEATHER_TYPE = """
            INSERT INTO WEATHER_DIRECTORY(TYPE)
            VALUES (?)
            """;

    public static final String UPDATE_CITY_BY_ID = """
            UPDATE CITY
            SET NAME = ?
            WHERE ID = ?
            """;

    public static final String UPDATE_WEATHER_BY_ID = """
            UPDATE WEATHER
            SET TEMPERATURE = ?, CITY_ID = ?, WEATHER_TYPE_ID = ?
            WHERE ID = ?
            """;

    public static final String UPDATE_WEATHER_TYPE_BY_ID = """
            UPDATE WEATHER_DIRECTORY
            SET TYPE = ?
            WHERE ID = ?
            """;

}
