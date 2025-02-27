import java.time.LocalDate;
import java.time.LocalTime;
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
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6));

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
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31));

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
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(10, 1));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ATTEND);
    }

    @DisplayName("시작 시간 이전에 출석해도 출석이다.")
    @Test
    void check4() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3),  LocalTime.of(9, 57));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ATTEND);
    }

    @DisplayName("시작 시간이 한참지나 출석하면 결석이다.")
    @Test
    void check5() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 3), LocalTime.of(12, 0));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ABSENT);
    }

    @DisplayName("월요일은 13시 5분 초과 시 지각이다.")
    @Test
    void check6() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(13, 6));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.LATE);
    }

    @DisplayName("월요일은 13시 30분 초과 시 결석이다.")
    @Test
    void check7() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(13, 31));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ABSENT);
    }

    @DisplayName("월요일은 13시로부터 5분까지는 출석이다.")
    @Test
    void check8() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(13, 5));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ATTEND);
    }

    @DisplayName("월요일은 13시 이전에 출석해도 출석이다.")
    @Test
    void check9() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(11, 31));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ATTEND);
    }

    @DisplayName("월요일은 13시가 한참지나 출석하면 결석이다.")
    @Test
    void check10() {
        // given
        AttendanceTimeChecker checker = new AttendanceTimeChecker();

        // when
        AttendPolicy policy = checker.attendanceCheck(LocalDate.of(2024, 12, 2), LocalTime.of(14, 0));

        // then
        Assertions.assertThat(policy)
                .isEqualTo(AttendPolicy.ABSENT);
    }
}
