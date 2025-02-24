package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceTest {

    @Test
    @DisplayName("닉네임과 등교 시간이 입력되면 출석")
    void attendance_basic_test1() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("모루", "10:00")).isEqualTo("출석");
    }

    @Test
    @DisplayName("5분 초과는 지각이다")
    void attendance_late_test1() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("모루", "10:06")).isEqualTo("지각");
    }

    @Test
    @DisplayName("30분 초과는 결석이다")
    void attendance_absence_test1() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("모루", "10:31")).isEqualTo("결석");
    }

}
