package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.util.FileReader;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewsTest {
    private Crews crews;

    @BeforeEach
    void setUp() {
        crews = new Crews();
        FileReader reader = new FileReader();
        List<List<String>> attendanceRecords = reader.readResource("attendances.csv");

        crews.initCrews(attendanceRecords);
    }

    @DisplayName("이름으로 크루를 조회한다.")
    @Test
    void findCrewByName() {
        assertThat(crews.findCrew("빙티")).isEqualTo(new Crew("빙티"));
    }
}