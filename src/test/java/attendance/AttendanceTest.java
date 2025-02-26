package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceArgumentException;

public class AttendanceTest {

    @Test
    @DisplayName("등교 시간을 입력하면, 출석 상태를 저장한다.")
    void test_attendance() {
        var time = LocalDateTime.of(2024, 12, 13, 10, 1);
        var attendance = Attendance.from(time);

        assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("출석 시간보다 5분 초과되어 출석할 때, 지각 처리한다.")
    void test_attendanceOfLate() {
        var time = LocalDateTime.of(2024, 12, 13, 10, 6);
        var attendance = Attendance.from(time);

        assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("출석 시간보다 30분 초과되어 출석할 때, 결석 처리한다.")
    void test_attendanceOfAbsence() {
        var time = LocalDateTime.of(2024, 12, 13, 10, 31);
        var attendance = Attendance.from(time);

        assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("월요일에 출석할 때, 교육시간이 13시부터이다.")
    void test_attendanceOnMonday() {
        var time = LocalDateTime.of(2024, 12, 16, 13, 4);
        var attendance = Attendance.from(time);

        assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("월요일은 13:05 이후 출석할 경우, 지각 처리된다.")
    void test_attendanceOfLateOnMonday() {
        var time = LocalDateTime.of(2024, 12, 16, 13, 14);
        var attendance = Attendance.from(time);

        assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("월요일은 13:30 이후 출석할 경우, 결석 처리된다.")
    void test_attendanceOfAbsenceOnMonday() {
        var time = LocalDateTime.of(2024, 12, 16, 13, 34);
        var attendance = Attendance.from(time);

        assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("출석하는 날짜가 주말인 경우, 예외가 발생한다.")
    void error_attendanceOnWeekend() {
        var time = LocalDateTime.of(2024, 12, 14, 10, 34);

        Assertions.assertThatThrownBy(() -> Attendance.from(time))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 23})
    @DisplayName("캠퍼스 운영시간 외에 출석할 경우, 예외가 발생한다.")
    void error_attendanceOutOfRangeOnSchedule(int hour) {
        var time = LocalDateTime.of(2024, 12, 13, hour, 34);

        Assertions.assertThatThrownBy(() -> Attendance.from(time))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessage("[ERROR] 등교시간에만 출석 가능합니다.");
    }

    @Test
    @DisplayName("출석하는 날짜가 공휴일인 경우, 예외가 발생한다.")
    void error_attendanceOnHoliday() {
        var time = LocalDateTime.of(2024, 12, 25, 10, 34);

        Assertions.assertThatThrownBy(() -> Attendance.from(time))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }
}
