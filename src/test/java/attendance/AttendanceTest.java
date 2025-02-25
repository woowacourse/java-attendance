package attendance;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다")
    void attendanceTest() {
        // given
        Crew crew = new Crew("pobi");
        Crews crews = new Crews();
        crews.add(crew);

        // when then
        assertThatCode(() -> {
            Crew found = crews.get("pobi");
            found.attendance(LocalDate.of(2025, 02, 25), LocalTime.of(10, 00));
        });
    }
}
