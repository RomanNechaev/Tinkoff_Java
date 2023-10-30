package tinkoff.training;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestContainersH2Test {
    @Container
    public static GenericContainer h2 = new GenericContainer(DockerImageName.parse("oscarfonts/h2"))
            .withExposedPorts(1521, 81)
            .withEnv("H2_OPTIONS", "-ifNotExists")
            .waitingFor(Wait.defaultWaitStrategy());

    @Test
    public void testSelectFromTestTable() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");

        dataSource.setUrl("jdbc:h2:tcp://localhost:" + h2.getMappedPort(1521) + "/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute(
                "CREATE TABLE test (" +
                        "    ID INT PRIMARY KEY," +
                        "    VAL VARCHAR(255)" +
                        ")");
        jdbcTemplate.update(
                "INSERT INTO test (ID, VAL) VALUES (?, ?)",
                1, "Test Val");
        String testName = jdbcTemplate.queryForObject("SELECT VAL FROM test WHERE id = 1", String.class);

        assertThat(testName).isEqualTo("Test Val");
    }
}
