package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("출석")
    @Test
    void test1() {
        int result = Attendance.checkAttendance(10, 5);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("지각")
    @Test
    void test2() {
        int result = Attendance.checkAttendance(10, 6);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("결석")
    @Test
    void test3() {
        int result = Attendance.checkAttendance(10, 31);
        assertThat(result).isEqualTo(-1);
    }

    @DisplayName("월요일 출석")
    @Test
    void test4() {
        int result = Attendance.checkMondayAttendance(13, 5);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("월요일 지각")
    @Test
    void test5() {
        int result = Attendance.checkMondayAttendance(13, 6);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("월요일 결석")
    @Test
    void test6() {
        int result = Attendance.checkMondayAttendance(13, 31);
        assertThat(result).isEqualTo(-1);
    }

    @DisplayName("캠퍼스 운영 시작 시간 전 출석 시 예외 발생")
    @Test
    void test7() {
        assertThatThrownBy(() -> Attendance.method1(7, 59))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 시작 시간 이후 출석 시 통과")
    @Test
    void test8() {
        assertThatCode(() -> Attendance.method1(8, 0)).doesNotThrowAnyException();
    }

    @DisplayName("캠퍼스 운영 종료 시간 이후 출석 시 예외 발생")
    @Test
    void test9() {
        assertThatThrownBy(() -> Attendance.method1(23, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 종료 시간 이전 출석 시 통과")
    @Test
    void test10() {
        assertThatCode(() -> Attendance.method1(23, 0)).doesNotThrowAnyException();
    }

    @DisplayName("공휴일 출석 실패")
    @Test
    void test11() {
        assertThatThrownBy(() -> Attendance.method2(25))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 25일 수요일은 등교일이 아닙니다.");
    }

    @DisplayName("주말 출석 실패")
    @Test
    void test12() {
        assertThatThrownBy(() -> Attendance.method2(22))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 22일 일요일은 등교일이 아닙니다.");
    }

    @DisplayName("평일 출석 통과")
    @Test
    void test13() {
        assertThatCode(() -> Attendance.method2(24)).doesNotThrowAnyException();
    }

    @DisplayName("출석 정보 저장 성공 ")
    @Test
    void test14() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");

        assertThatCode(() -> attendance.add(localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("출석 정보 저장 실패")
    @Test
    void test15() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");

        attendance.add(localDateTime);

        assertThatThrownBy(() -> attendance.add(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }
}
