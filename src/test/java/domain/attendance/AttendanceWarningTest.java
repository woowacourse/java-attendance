package domain.attendance;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceWarningTest {
    @DisplayName("(지각 3회 당 결석 1회를 포함하여) 결석이 2번이면 경고 상태이다")
    @Test
    void test() {
        AttendanceWarning attendanceWarning = AttendanceWarning.determineAttendanceWarning(2);
        Assertions.assertThat(attendanceWarning.getStatus()).isEqualTo("경고");
    }

    @DisplayName("(지각 3회 당 결석 1회를 포함하여) 결석이 3번 이상 5번 이하면 면담 상태이다")
    @Test
    void test2() {
        AttendanceWarning attendanceWarning = AttendanceWarning.determineAttendanceWarning(5);
        Assertions.assertThat(attendanceWarning.getStatus()).isEqualTo("면담");
    }

    @DisplayName("(지각 3회 당 결석 1회를 포함하여) 결석이 6번 이상이면 제적 상태이다")
    @Test
    void test3() {
        AttendanceWarning attendanceWarning = AttendanceWarning.determineAttendanceWarning(6);
        Assertions.assertThat(attendanceWarning.getStatus()).isEqualTo("제적");
    }
}