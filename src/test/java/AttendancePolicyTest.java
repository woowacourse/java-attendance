import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendancePolicyTest {

    private LocalDate weekday;
    private LocalDate weekend;

    @BeforeEach
    void setUp() {
        weekday = LocalDate.of(2024, 12, 13);
        weekend = LocalDate.of(2024, 12, 14);
    }

    @ParameterizedTest
    @CsvSource(value = {"09:59, 출석", "10:06, 지각", "10:31, 결석"})
    void 해당_날짜의_출석_지각_결석_여부를_판단한다(String time, String expected) {
        LocalTime todayTime = LocalTime.of(
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
        LocalDateTime attendanceTime = LocalDateTime.of(weekday, todayTime);

        AttendanceStatus status = new AttendancePolicy().getAttendanceStatus(attendanceTime);
        assertThat(status.getStatus()).isEqualTo(expected);
    }

    @Test
    void 출석_날짜가_주말이면_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendancePolicy().validateIsWeekDays(weekend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"07:59", "23:01"})
    void 캠퍼스_운영_시간이_아니면_예외가_발생한다(String time) {
        LocalTime todayTime = LocalTime.of(
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));

        assertThatThrownBy(() -> new AttendancePolicy().validateCampusOpen(todayTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }
}
