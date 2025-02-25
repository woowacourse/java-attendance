import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTimeTest {
    @DisplayName("AttendanceDateTime 생성 테스트")
    @Test
    void dateTimeTest1() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 25, 14, 39);
        Assertions.assertThat(attendanceDateTime).isInstanceOf(AttendanceDateTime.class);
    }

    @DisplayName("AttendanceDateTime 휴일 테스트")
    @Test
    void dateTimeTest2() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 25, 0, 0);
        Assertions.assertThat(attendanceDateTime.isRestDay()).isTrue();
    }

    @DisplayName("AttendanceDateTime 휴일 테스트")
    @Test
    void dateTimeTest3() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 0, 0);
        Assertions.assertThat(attendanceDateTime.isRestDay()).isFalse();
    }
}
