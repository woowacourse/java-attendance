package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static attendance.error.ErrorMessage.NOT_OPERATING_HOLIDAY;
import static attendance.error.ErrorMessage.NOT_OPERATING_TIME;
import static attendance.error.ErrorMessage.NOT_OPERATING_WEEKEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancePolicyTest {

    @DisplayName("주어진 날짜가 평일일 경우, 테스트를 통과해야 한다.")
    @Test
    void given_weekdays_then_pass() {
        LocalDate currentDate = LocalDate.of(2025, 2, 21); // 금요일
        LocalTime attendanceTime = LocalTime.of(10, 0);
        AttendancePolicy.checkAttendanceType(currentDate, attendanceTime);
        assertDoesNotThrow(
            () -> AttendancePolicy.checkAttendanceType(currentDate, attendanceTime));
    }

    @DisplayName("주어진 날짜가 주말일 경우, 예외를 발생시켜야 한다.")
    @Test
    void given_weekend_then_throw_exception() {
        LocalDate currentDate = LocalDate.of(2025, 2, 22);
        assertThatThrownBy(() -> AttendancePolicy.ifHolidayOrWeekendsThrowException(currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_OPERATING_WEEKEND.getMessage());
    }

    @DisplayName("주어진 날짜가 공휴일인 경우, 예외를 발생시켜야 한다.")
    @Test
    void given_holiday_then_throw_exception() {
        LocalDate currentDate = LocalDate.of(2024, 12, 25);
        assertThatThrownBy(() -> AttendancePolicy.ifHolidayOrWeekendsThrowException(currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_OPERATING_HOLIDAY.getMessage());
    }

    @DisplayName("운영시간일 경우, 테스트를 통과해야 한다.")
    @Test
    void operating_time_then_throw_exception() {
        LocalDate currentDate = LocalDate.of(2025, 2, 21);
        LocalTime attendanceTime = LocalTime.of(8, 0);
        assertDoesNotThrow(
            () -> AttendancePolicy.checkAttendanceType(currentDate, attendanceTime));
    }

    @DisplayName("운영시간 아닐경우, 예외를 발생시켜야 한다.")
    @Test
    void not_operating_time_then_throw_exception() {
        LocalTime attendanceTime = LocalTime.of(7, 0);
        LocalDate currentDate = LocalDate.of(2025, 2, 21);

        assertThatThrownBy(() -> AttendancePolicy.checkAttendanceType(currentDate, attendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_OPERATING_TIME.getMessage());
    }

    @DisplayName("지각 시간이 4분일 경우, Attendance를 반환한다.")
    @Test
    void late_time_is_4_then_return_attendance() {
        LocalDate currentDate = LocalDate.of(2025, 2, 21);
        LocalTime attendanceTime = LocalTime.of(10, 4);
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(currentDate,
            attendanceTime);
        assertThat(attendanceType).isEqualTo(ATTENDANCE);
    }

    @DisplayName("지각 시간이 5분일 경우, LATE를 반환한다.")
    @Test
    void late_time_is_5_then_return_attendance() {
        LocalDate currentDate = LocalDate.of(2025, 2, 21);
        LocalTime attendanceTime = LocalTime.of(10, 5);
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(currentDate,
            attendanceTime);
        assertThat(attendanceType).isEqualTo(LATE);
    }

    @DisplayName("지각 시간이 29분일 경우, LATE를 반환한다.")
    @Test
    void late_time_is_29_then_return_attendance() {
        LocalDate currentDate = LocalDate.of(2025, 2, 21);
        LocalTime attendanceTime = LocalTime.of(10, 29);
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(currentDate,
            attendanceTime);
        assertThat(attendanceType).isEqualTo(LATE);
    }

    @DisplayName("지각 시간이 30분일 경우, ABSENCE 반환한다.")
    @Test
    void late_time_is_30_then_return_attendance() {
        LocalDate currentDate = LocalDate.of(2025, 2, 21);
        LocalTime attendanceTime = LocalTime.of(10, 30);
        AttendanceType attendanceType = AttendancePolicy.checkAttendanceType(currentDate,
            attendanceTime);
        assertThat(attendanceType).isEqualTo(ABSENCE);
    }
}
