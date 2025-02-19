package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceCheckerTest {
    @DisplayName("출석")
    @Test
    void test1() {
        int result = AttendanceChecker.checkAttendance(10, 5);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("지각")
    @Test
    void test2() {
        int result = AttendanceChecker.checkAttendance(10, 6);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("결석")
    @Test
    void test3() {
        int result = AttendanceChecker.checkAttendance(10, 31);
        assertThat(result).isEqualTo(-1);
    }

    @DisplayName("월요일 출석")
    @Test
    void test4() {
        int result = AttendanceChecker.checkMondayAttendance(13, 5);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("월요일 지각")
    @Test
    void test5() {
        int result = AttendanceChecker.checkMondayAttendance(13, 6);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("월요일 결석")
    @Test
    void test6() {
        int result = AttendanceChecker.checkMondayAttendance(13, 31);
        assertThat(result).isEqualTo(-1);
    }

    @DisplayName("캠퍼스 운영 시작 시간 전 출석 시 예외 발생")
    @Test
    void test7() {
        assertThatThrownBy(() -> AttendanceChecker.checkCampusHour(7, 59))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 시작 시간 이후 출석 시 통과")
    @Test
    void test8() {
        assertThatCode(() -> AttendanceChecker.checkCampusHour(8, 0)).doesNotThrowAnyException();
    }

    @DisplayName("캠퍼스 운영 종료 시간 이후 출석 시 예외 발생")
    @Test
    void test9() {
        assertThatThrownBy(() -> AttendanceChecker.checkCampusHour(23, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 종료 시간 이전 출석 시 통과")
    @Test
    void test10() {
        assertThatCode(() -> AttendanceChecker.checkCampusHour(23, 0)).doesNotThrowAnyException();
    }

    @DisplayName("공휴일 출석 실패")
    @Test
    void test11() {
        assertThatThrownBy(() -> AttendanceChecker.checkCampusDay(25))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }

    @DisplayName("주말 출석 실패")
    @Test
    void test12() {
        assertThatThrownBy(() -> AttendanceChecker.checkCampusDay(22))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 22일 일요일은 등교일이 아닙니다.");
    }

    @DisplayName("평일 출석 통과")
    @Test
    void test13() {
        assertThatCode(() -> AttendanceChecker.checkCampusDay(24)).doesNotThrowAnyException();
    }

}
