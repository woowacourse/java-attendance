package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("캠퍼스 운영 시간 외 예외 발생")
    @Test
    void test7() {
        assertThatThrownBy(Attendance.method1(7, 59))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 시간 내 통과")
    @Test
    void test8() {
        assertThatCode(Attendance.method1(8, 0)).doesNotThrowAnyException();
    }
}
