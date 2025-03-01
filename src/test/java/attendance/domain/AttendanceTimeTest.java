package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @MethodSource("provideAttendanceTime")
    @DisplayName("주말에 출석할 경우, 예외가 발생해야 한다")
    void on_weekday_attendance_then_throw_exception(LocalDateTime attendanceDateTime) {
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

    private static Stream<Arguments> provideAttendanceTime() {
        LocalDateTime attendanceDateTime1 = LocalDateTime.of(2024, 12, 7, 10, 0);
        LocalDateTime attendanceDateTime2 = LocalDateTime.of(2024, 12, 8, 10, 0);
        return Stream.of(Arguments.of(attendanceDateTime1), Arguments.of(attendanceDateTime2));
    }
}
