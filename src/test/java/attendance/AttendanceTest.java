package attendance;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    public static final LocalDate DATE = LocalDate.of(2025, 02, 25);
    public static final LocalTime TIME = LocalTime.of(10, 00);

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
            found.attendance(DATE, TIME);
        });
    }

    @Test
    @DisplayName("이미 출석한 경우 다시 출석을 시도하면 예외를 반환한다")
    void attendanceExceptionTest() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(DATE, TIME);

        // when then
        assertThatThrownBy(() -> {
            crew.attendance(DATE, TIME.plusMinutes(30));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
