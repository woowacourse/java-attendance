package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import attendance.exception.AttendanceArgumentException;

class AttendanceTest {

    @Test
    @DisplayName("시간을 입력받으면, 출석 상태를 저장한다.")
    void test_AttendanceCorrect() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 0);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.status()).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("출석 시간이 10:05분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceLate() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 10);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.status()).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("출석 시간이 10:30분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceAbsence() {
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 35);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.status()).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    @DisplayName("월요일은 출석 시간이 13:05분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceLateOnMonday() {
        var dateTime = LocalDateTime.of(2024, 12, 16, 13, 10);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.status()).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("월요일은 출석 시간이 13:30분 초과일 경우, 지각으로 저장한다.")
    void test_AttendanceAbsenceOnMonday() {
        var dateTime = LocalDateTime.of(2024, 12, 16, 13, 35);
        var assertion = new Attendance(dateTime);

        assertThat(assertion.status()).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 23})
    @DisplayName("캠퍼스 운영 시간 외에 출석할 경우, 예외가 발생한다.")
    void error_AttendanceOutOfSchedule(int hour) {
        var dateTime = LocalDateTime.of(2024, 12, 16, hour, 35);

        assertThatThrownBy(() -> new Attendance(dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("운영시간 외에 출석할 수 없습니다.");
    }
}
