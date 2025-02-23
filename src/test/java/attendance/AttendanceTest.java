package attendance;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;

public class AttendanceTest {

    @Test
    @DisplayName("등교 시간을 입력하면, 출석한다.")
    void test_attendance() {
        var time = LocalDateTime.of(2024, 12, 14, 10, 1);
        var attendance = new Attendance(time);

        Assertions.assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("출석 시간보다 5분 초과되어 출석할 때, 지각 처리한다.")
    void test_attendanceOfLate() {
        var time = LocalDateTime.of(2024, 12, 14, 10, 6);
        var attendance = new Attendance(time);

        Assertions.assertThat(attendance.attendanceStatus())
            .isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("출석 시간보다 30분 초과되어 출석할 때, 결석 처리된다.")
    void test_attendanceOfAbsence() {

    }

    @Test
    @Disabled
    @DisplayName("월요일에 출석할 경우, 출석 시간이 13시부터이다.")
    void test_attendanceOnMonday() {
    }

    @Test
    @Disabled
    @DisplayName("월요일에 5분 늦게 출석할 경우, 지각 처리된다.")
    void test_attendanceOfLateOnMonday() {
    }

    @Test
    @Disabled
    @DisplayName("월요일에 30분 늦게 출석할 경우, 결석 처리된다.")
    void test_attendanceOfAbsenceOnMonday() {
    }

    @Test
    @DisplayName("출석할 때, 시간의 형식은 24시간 형식이다.")
    void test_attendanceTimeFormat() {

    }

    @Test
    @DisplayName("다시 출석할 경우, 예외가 발생한다.")
    void error_retireAttendance() {

    }

    @Test
    @DisplayName("출석하는 날짜가 주말인 경우, 예외가 발생한다.")
    void error_attendanceOnWeekend() {

    }

    @Test
    @DisplayName("캠퍼스 운영시간 외에 출석할 경우, 예외가 발생한다.")
    void error_attendanceOutOfRangeOnSchedule() {

    }

    @Test
    @DisplayName("출석하는 날짜가 공휴일인 경우, 예외가 발생한다.")
    void error_attendanceOnHoliday() {
        //given&when

        //then
    }
}
