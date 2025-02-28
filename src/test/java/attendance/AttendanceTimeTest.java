package attendance;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {

    @DisplayName("토요일에 출석할 경우, 예외가 발생해야 한다")
    @Test
    void on_weekday_attendance_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 7, 10, 0);
        assertThatThrownBy(() -> AttendanceTime.from(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주말에는 등교할 수 없습니다.");
    }

    @DisplayName("일요일에 출석 하려고 하는 경우, 예외가 발생해야 한다.")
    @Test
    void given_saturday_attendance_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 8, 10, 0);
        assertThatThrownBy(() -> AttendanceTime.from(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주말에는 등교할 수 없습니다.");
    }

    @DisplayName("캠퍼스 운영시간이 아닌 07:00에 출석 하려는 경우, 예외가 발생해야 한다.")
    @Test
    void given_attendance_time_07_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 7, 0);
        assertThatThrownBy(() -> AttendanceTime.from(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영시간이 아닌 23:01에 출석 할 경우, 예외가 발생해야 한다.")
    @Test
    void given_attendance_time_23_01_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 23, 1);
        assertThatThrownBy(() -> AttendanceTime.from(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영시간인 08:00에 출석할 경우, 예외가 발생해서는 안 된다.")
    @Test
    void given_attendance_time_08_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 8, 0);
        assertThatCode(() -> AttendanceTime.from(attendanceDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("캠퍼스 운영시간인 23:00에 출석할 경우, 예외가 발생해서는 안 된다.")
    @Test
    void given_attendance_time_23_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 23, 0);
        assertThatCode(() -> AttendanceTime.from(attendanceDateTime)).doesNotThrowAnyException();
    }
}
