package domain.attendance;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimeTest {
    @DisplayName("월요일은 13시 5분까지 출석해야 출석으로 인정된다")
    @Test
    void test() {
        boolean isAttendanceSuccess = AttendanceTime.isAttendance(1, LocalDateTime.of(2025, 2, 17, 13, 5));
        boolean isAttendanceFail = AttendanceTime.isAttendance(1, LocalDateTime.of(2025, 2, 17, 13, 6));
        Assertions.assertThat(isAttendanceSuccess).isTrue();
        Assertions.assertThat(isAttendanceFail).isFalse();
    }

    @DisplayName("월요일은 13시 30분 초과 출석하면 결석이다")
    @Test
    void test2() {
        boolean isAbsenceFalse = AttendanceTime.isAbsence(1, LocalDateTime.of(2025, 2, 17, 13, 30));
        boolean isAbsenceTrue = AttendanceTime.isAbsence(1, LocalDateTime.of(2025, 2, 17, 13, 31));
        Assertions.assertThat(isAbsenceFalse).isFalse();
        Assertions.assertThat(isAbsenceTrue).isTrue();
    }

    @DisplayName("월요일을 제외한 평일은 10시 5분까지 출석해야 출석으로 인정된다")
    @Test
    void test3() {
        boolean isAttendanceSuccess = AttendanceTime.isAttendance(2, LocalDateTime.of(2025, 2, 18, 10, 5));
        boolean isAttendanceFail = AttendanceTime.isAttendance(2, LocalDateTime.of(2025, 2, 18, 10, 6));
        Assertions.assertThat(isAttendanceSuccess).isTrue();
        Assertions.assertThat(isAttendanceFail).isFalse();
    }

    @DisplayName("월요일을 제외한 평일은 10시 30분 초과 출석하면 결석이다")
    @Test
    void test4() {
        boolean isAbsenceFalse = AttendanceTime.isAbsence(2, LocalDateTime.of(2025, 2, 18, 10, 30));
        boolean isAbsenceTrue = AttendanceTime.isAbsence(2, LocalDateTime.of(2025, 2, 18, 10, 31));
        Assertions.assertThat(isAbsenceFalse).isFalse();
        Assertions.assertThat(isAbsenceTrue).isTrue();
    }

}