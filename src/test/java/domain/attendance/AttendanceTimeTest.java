package domain.attendance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTimeTest {
    @DisplayName("월요일은 13시 5분까지 출석해야 출석으로 인정된다")
    @Test
    void test() {
        boolean isAttendanceSuccess = AttendanceTime.isAttendance(DayOfWeek.MONDAY,
                LocalDateTime.of(2025, 2, 17, 13, 5));
        Assertions.assertThat(isAttendanceSuccess).isTrue();
    }

    @DisplayName("월요일은 13시 6분부터 13시 30분까지 출석하면 지각이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 30})
    void test1(int minute) {
        boolean isAttendanceFail = AttendanceTime.isAttendance(
                DayOfWeek.MONDAY, LocalDateTime.of(2025, 2, 17, 13, minute));
        Assertions.assertThat(isAttendanceFail).isFalse();
    }

    @DisplayName("월요일은 13시 30분 초과 출석하면 결석이다")
    @Test
    void test2() {
        boolean isAbsenceTrue = AttendanceTime.isAbsence(DayOfWeek.MONDAY, LocalDateTime.of(2025, 2, 17, 13, 31));
        Assertions.assertThat(isAbsenceTrue).isTrue();
    }

    @DisplayName("월요일을 제외한 평일은 10시 5분까지 출석해야 출석으로 인정된다")
    @Test
    void test3() {
        boolean isAttendanceSuccess = AttendanceTime.isAttendance(DayOfWeek.TUESDAY,
                LocalDateTime.of(2025, 2, 18, 10, 5));
        Assertions.assertThat(isAttendanceSuccess).isTrue();
    }

    @DisplayName("월요일을 제외한 평일은 10시 6분부터 10시 30분까지 출석하면 지각이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 30})
    void test4(int minute) {
        boolean isAttendance = AttendanceTime.isAttendance(DayOfWeek.TUESDAY,
                LocalDateTime.of(2025, 2, 18, 10, minute));
        Assertions.assertThat(isAttendance).isFalse();
    }

    @DisplayName("월요일을 제외한 평일은 10시 30분 초과 출석하면 결석이다")
    @Test
    void test5() {
        boolean isAbsenceTrue = AttendanceTime.isAbsence(DayOfWeek.TUESDAY, LocalDateTime.of(2025, 2, 18, 10, 31));
        Assertions.assertThat(isAbsenceTrue).isTrue();
    }

}