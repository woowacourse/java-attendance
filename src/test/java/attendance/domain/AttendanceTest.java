package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    @DisplayName("시간을 입력받으면, 출석 상태를 저장한다.")
    void test_AttendanceCorrect() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 0);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.state()).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("출석 시간이 10:05분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceLate() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 10);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.state()).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("출석 시간이 10:30분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceAbsence() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 35);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.state()).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("월요일은 출석 시간이 13:05분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceLateOnMonday() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 13, 10);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.state()).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("월요일은 출석 시간이 13:30분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceAbsenceOnMonday() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 13, 35);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.state()).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
