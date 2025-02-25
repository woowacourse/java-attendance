package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void crewTest() {
        Crew crew = new Crew("pobi");

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        assertThatCode(crew.checkAttendance(date, time)).doesNotThrowAnyException();
    }
}
