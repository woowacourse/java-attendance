package attendance;

import static attendance.AttendanceType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTypeTest {

    @DisplayName("화요일 10:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_tuesday_10_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        assertAttendanceType(attendanceDateTime, ATTENDANCE);
    }

    @DisplayName("월요일 13:00에 출석했을 경우, 출석을 반환해야 한다")
    @Test
    void given_monday_13_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        assertAttendanceType(attendanceDateTime, ATTENDANCE);
    }

    @DisplayName("월요일 13:05에 출석했을 경우, 지각을 반환해야 한다")
    @Test
    void given_monday_13_05_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
        assertAttendanceType(attendanceDateTime, LATE);
    }

    @DisplayName("월요일 13:30에 출석했을 경우, 결석을 반환해야 한다")
    @Test
    void given_monday_13_30_then_return_attendance() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);
        assertAttendanceType(attendanceDateTime, ABSENCE);
    }

    @DisplayName("캠퍼스 운영시간이 아닌 07:00에 출석 할 경우, 예외가 발생해야 한다.")
    @Test
    void given_attendance_time_07_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 7, 0);
        assertThatThrownBy(() -> AttendanceType.decideAttendanceType(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("캠퍼스 운영시간이 아닌 23:01에 출석 할 경우, 예외가 발생해야 한다.")
    @Test
    void given_attendance_time_23_01_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 23, 1);
        assertThatThrownBy(() -> AttendanceType.decideAttendanceType(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("캠퍼스 운영시간인 08:00에 출석할 경우, 예외가 발생해서는 안 된다.")
    @Test
    void given_attendance_time_08_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 8, 0);
        assertThatCode(() -> AttendanceType.decideAttendanceType(attendanceDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("캠퍼스 운영시간인 23:00에 출석할 경우, 예외가 발생해서는 안 된다.")
    @Test
    void given_attendance_time_23_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 23, 0);
        assertThatCode(() -> AttendanceType.decideAttendanceType(attendanceDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("토요일에 출석 하려고 하는 경우, 예외가 발생해야 한다.")
    @Test
    void given_saturday_attendance_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 7, 10, 0);
        assertThatThrownBy(() -> AttendanceType.decideAttendanceType(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("일요일에 출석 하려고 하는 경우, 예외가 발생해야 한다.")
    @Test
    void given_sunday_attendance_then_throw_exception() {
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 8, 10, 0);
        assertThatThrownBy(() -> AttendanceType.decideAttendanceType(attendanceDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private void assertAttendanceType(LocalDateTime attendanceTime, AttendanceType expectedType) {
        AttendanceType attendanceType = AttendanceType.decideAttendanceType(attendanceTime);
        assertThat(attendanceType).isEqualTo(expectedType);
    }
}
