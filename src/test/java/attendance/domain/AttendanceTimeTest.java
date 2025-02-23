package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;
import static attendance.error.ErrorMessage.NOT_OPERATING_HOLIDAY;
import static attendance.error.ErrorMessage.NOT_OPERATING_TIME;
import static attendance.error.ErrorMessage.NOT_OPERATING_WEEKEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {

    @DisplayName("주어진 날짜가 평일일 경우, 테스트를 통과해야 한다.")
    @Test
    void given_weekdays_then_pass() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 10, 0);
        assertDoesNotThrow(() -> AttendanceTime.from(localDateTime));
    }

    @DisplayName("주어진 날짜가 주말일 경우, 예외를 발생시켜야 한다.")
    @Test
    void given_weekend_then_throw_exception() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 22, 10, 0);
        assertThatThrownBy(() -> AttendanceTime.from(localDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_OPERATING_WEEKEND.getMessage());
    }

    @DisplayName("주어진 날짜가 공휴일인 경우, 예외를 발생시켜야 한다.")
    @Test
    void given_holiday_then_throw_exception() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        assertThatThrownBy(() -> AttendanceTime.from(localDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_OPERATING_HOLIDAY.getMessage());
    }

    @DisplayName("운영시간일 경우, 테스트를 통과해야 한다.")
    @Test
    void operating_time_then_throw_exception() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 8, 0);
        assertDoesNotThrow(() -> AttendanceTime.from(localDateTime));
    }

    @DisplayName("운영시간 아닐경우, 예외를 발생시켜야 한다.")
    @Test
    void not_operating_time_then_throw_exception() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 7, 0);
        assertThatThrownBy(() -> AttendanceTime.from(localDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_OPERATING_TIME.getMessage());
    }

    @DisplayName("지각 시간이 4분일 경우, Attendance를 반환한다.")
    @Test
    void late_time_is_4_then_return_attendance() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 10, 4);
        assertAttendanceType(localDateTime, ATTENDANCE);
    }

    @DisplayName("지각 시간이 5분일 경우, LATE를 반환한다.")
    @Test
    void late_time_is_5_then_return_attendance() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 10, 5);
        assertAttendanceType(localDateTime, LATE);
    }

    @DisplayName("지각 시간이 29분일 경우, LATE를 반환한다.")
    @Test
    void late_time_is_29_then_return_attendance() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 10, 29);
        assertAttendanceType(localDateTime, LATE);
    }

    @DisplayName("지각 시간이 30분일 경우, ABSENCE 반환한다.")
    @Test
    void late_time_is_30_then_return_attendance() {
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 21, 10, 30);
        assertAttendanceType(localDateTime, ABSENCE);
    }

    private void assertAttendanceType(LocalDateTime localDateTime,
        AttendanceType expectedAttendanceType) {
        AttendanceTime attendanceTime = AttendanceTime.from(localDateTime);
        AttendanceType attendanceType = attendanceTime.calculateAttendanceType();
        assertThat(attendanceType).isEqualTo(expectedAttendanceType);
    }
}
