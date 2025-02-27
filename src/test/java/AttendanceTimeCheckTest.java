import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTimeCheckTest {

    @DisplayName("시작 시간 5분 초과 출석은 지각이다.")
    @Test
    void check() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck("10:06");

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.LATE);
    }
}
