import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendTime;
import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendTimeTest {

    @DisplayName("출석이 정상인지를 파악할 수 있다")
    @Test
    void shouldDetermineAttendanceStatusAsAttendedWhenWithinGracePeriod() {
        AttendTime attendTime = new AttendTime(LocalDate.of(2024, 12, 16), LocalTime.of(10, 58));
        assertThat(attendTime.checkAttendanceStatus()).isEqualTo(AttendanceStatus.ATTENDED);
    }

    @DisplayName("출석이 지각인지를 파악할 수 있다")
    @Test
    void shouldDetermineAttendanceStatusAsLateWhenPastGracePeriod() {
        AttendTime attendTime = new AttendTime(LocalDate.of(2024, 12, 17), LocalTime.of(10, 6));
        assertThat(attendTime.checkAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("휴일에는 출석 할 수 없다")
    @Test
    void cannotAttendOnHoliday() {
        assertThatThrownBy(() -> new AttendTime(LocalDate.of(2024, 12, 25), LocalTime.of(10, 6)));
    }

    @DisplayName("주말에는 출석 할 수 없다")
    @Test
    void cannotAttendOnWeekend() {
        assertThatThrownBy(() -> new AttendTime(LocalDate.of(2024, 12, 15), LocalTime.of(10, 6)));
    }
}
