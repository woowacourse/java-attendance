package attendance.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import attendance.util.CSVReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {
    private static final Path path = Paths.get("src/main/resources/attendances.csv");

    private Crews crews;

    @BeforeEach
    void setUp() {
        crews = new Crews();

        List<List<String>> csvData = CSVReader.readCSV(path);

        crews.initCrews(csvData);
    }

    @DisplayName("등록된 크루들을 확인한다.")
    @Test
    void checkRegisteredCrews() {
        assertAll(
                () -> assertTrue(crews.contains(new Crew("빙티"))),
                () -> assertTrue(crews.contains(new Crew("빙봉"))),
                () -> assertTrue(crews.contains(new Crew("쿠키"))),
                () -> assertTrue(crews.contains(new Crew("이든"))),
                () -> assertTrue(crews.contains(new Crew("짱수")))
        );
    }

    @DisplayName("등록되지 않은 크루를 조회하면 에러를 발생한다.")
    @Test
    void checkUnregisteredCrews() {
        assertAll(
                () -> assertFalse(crews.contains(new Crew("엠제이"))),
                () -> assertFalse(crews.contains(new Crew("포비")))
        );
    }
}
