package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceCheckerTest {
    @DisplayName("출석")
    @Test
    void test1() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 24, 10, 5);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 24, 9, 5);
        AttendanceStatus result1 = AttendanceChecker.checkAttendance(localDateTime1);
        AttendanceStatus result2 = AttendanceChecker.checkAttendance(localDateTime2);
        assertThat(result1).isEqualTo(PRESENT);
        assertThat(result2).isEqualTo(PRESENT);
    }

    @DisplayName("지각")
    @Test
    void test2() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 6);
        AttendanceStatus result = AttendanceChecker.checkAttendance(localDateTime);
        assertThat(result).isEqualTo(LATENESS);
    }

    @DisplayName("결석")
    @Test
    void test3() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 31);
        AttendanceStatus result = AttendanceChecker.checkAttendance(localDateTime);
        assertThat(result).isEqualTo(ABSENCE);
    }

    @DisplayName("월요일 출석")
    @Test
    void test4() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 5);
        AttendanceStatus result = AttendanceChecker.checkAttendance(localDateTime);
        assertThat(result).isEqualTo(PRESENT);
    }

    @DisplayName("월요일 지각")
    @Test
    void test5() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 6);
        AttendanceStatus result = AttendanceChecker.checkAttendance(localDateTime);
        assertThat(result).isEqualTo(LATENESS);
    }

    @DisplayName("월요일 결석")
    @Test
    void test6() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 13, 31);
        AttendanceStatus result = AttendanceChecker.checkAttendance(localDateTime);
        assertThat(result).isEqualTo(ABSENCE);
    }

    @DisplayName("캠퍼스 운영 시작 시간 전 출석 시 예외 발생")
    @Test
    void test7() {
        assertThatThrownBy(() -> AttendanceChecker.validateCampusHour(LocalTime.of(7, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 시작 시간 이후 출석 시 통과")
    @Test
    void test8() {
        assertThatCode(() -> AttendanceChecker.validateCampusHour(LocalTime.of(8,0))).doesNotThrowAnyException();
    }

    @DisplayName("캠퍼스 운영 종료 시간 이후 출석 시 예외 발생")
    @Test
    void test9() {
        assertThatThrownBy(() -> AttendanceChecker.validateCampusHour(LocalTime.of(23, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 종료 시간 이전 출석 시 통과")
    @Test
    void test10() {
        assertThatCode(() -> AttendanceChecker.validateCampusHour(LocalTime.of(23, 0))).doesNotThrowAnyException();
    }

    @DisplayName("공휴일 출석 실패")
    @Test
    void test11() {
        assertThatThrownBy(() -> AttendanceChecker.validateCampusDay(LocalDate.of(2024, 12, 25)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }

    @DisplayName("주말 출석 실패")
    @Test
    void test12() {
        assertThatThrownBy(() -> AttendanceChecker.validateCampusDay(LocalDate.of(2024, 12, 22)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 22일 일요일은 등교일이 아닙니다.");
    }

    @DisplayName("평일 출석 통과")
    @Test
    void test13() {
        assertThatCode(() -> AttendanceChecker.validateCampusDay(LocalDate.of(2024, 12, 24))).doesNotThrowAnyException();
    }
}
