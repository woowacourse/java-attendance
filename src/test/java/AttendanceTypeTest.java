import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import type.AttendanceType;

public class AttendanceTypeTest {
    @Test
    @DisplayName("월요일에는 13시 6분에 등교하면 지각이다")
    void checkLate() {
        // given
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 13, 6);

        // when & then
        Assertions.assertThat(AttendanceType.findAttendanceTypeByDateTime(attendAt)).isEqualTo(AttendanceType.LATE);
    }

    @ParameterizedTest
    @DisplayName("월요일이 아닌 경우에는 10시 6분에 등교하면 지각이다")
    @ValueSource(ints = {3, 4, 5, 6})
    void checkLateExceptForMonday(int day) {
        // given
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, day, 10, 6);

        // when & then
        Assertions.assertThat(AttendanceType.findAttendanceTypeByDateTime(attendAt)).isEqualTo(AttendanceType.LATE);
    }

    @Test
    @DisplayName("월요일에는 13시 31분에 등교하면 지각이다")
    void checkAbsenceForMonday() {
        // given
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 13, 31);

        // when & then
        Assertions.assertThat(AttendanceType.findAttendanceTypeByDateTime(attendAt)).isEqualTo(AttendanceType.ABSENCE);
    }

    @ParameterizedTest
    @DisplayName("월요일이 아닌 경우에는 10시 31분에 등교하면 지각이다")
    @ValueSource(ints = {3, 4, 5, 6})
    void checkAbsenceExceptForMonday(int day) {
        // given
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, day, 10, 31);

        // when & then
        Assertions.assertThat(AttendanceType.findAttendanceTypeByDateTime(attendAt)).isEqualTo(AttendanceType.ABSENCE);
    }

    @ParameterizedTest
    @DisplayName("월요일이 아닌 경우에는 10시에 등교하면 출석이다")
    @ValueSource(ints = {3, 4, 5, 6})
    void checkPresentExceptForMonday(int day) {
        // given
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, day, 10, 0);

        // when & then
        Assertions.assertThat(AttendanceType.findAttendanceTypeByDateTime(attendAt)).isEqualTo(AttendanceType.PRESENT);
    }

    @Test
    @DisplayName("월요일에는 13시에 등교하면 출석이다")
    void checkPresentForMonday() {
        // given
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 13, 0);

        // when & then
        Assertions.assertThat(AttendanceType.findAttendanceTypeByDateTime(attendAt)).isEqualTo(AttendanceType.PRESENT);
    }

}
