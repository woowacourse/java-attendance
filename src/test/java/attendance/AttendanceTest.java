package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("출석")
    @Test
    void test1() {
        int result = Attendance.method(10, 5);
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("지각")
    @Test
    void test2() {
        int result = Attendance.method(10, 6);
        assertThat(result).isEqualTo(0);
    }

    @DisplayName("결석")
    @Test
    void test3() {
        int result = Attendance.method(10, 31);
        assertThat(result).isEqualTo(1);
    }
}
