package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    private Crew crew;

    @BeforeEach
    void setUp() {
        crew = new Crew("pobi");
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void crewTest() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        crew.checkAttendance(date, time);

        assertThat(crew.getAttendances().get(date)).isEqualTo(time);
    }
}
