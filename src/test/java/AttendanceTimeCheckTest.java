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

    @DisplayName("시작 시간 30분 초과 출석은 결석이다.")
    @Test
    void check2() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck("10:31");

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ABSENT);
    }

    @DisplayName("시작 시간으로부터 5분까지는 출석이다.")
    @Test
    void check3() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck("10:01");

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ATTEND);
    }
}
