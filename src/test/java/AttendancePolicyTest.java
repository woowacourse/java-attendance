import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendancePolicy;
import domain.AttendanceStatus;
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

    @ParameterizedTest
    @CsvSource(value = {"10:05, 출석", "10:06, 지각", "10:31, 결석"})
    void 화요일에서_금요일의_출석_지각_결석_여부를_판단한다(String time, String expected) {
        LocalTime todayTime = LocalTime.of(
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
        LocalDateTime attendanceTime = LocalDateTime.of(weekday, todayTime);

        AttendanceStatus status = new AttendancePolicy().getAttendanceStatus(attendanceTime);
        assertThat(status.getStatus()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"13:05, 출석", "13:06, 지각", "13:31, 결석"})
    void 월요일의_출석_지각_결석_여부를_판단한다(String time, String expected) {
        LocalTime todayTime = LocalTime.of(
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime attendanceTime = LocalDateTime.of(monday, todayTime);

        AttendanceStatus status = new AttendancePolicy().getAttendanceStatus(attendanceTime);
        assertThat(status.getStatus()).isEqualTo(expected);
    }

    @Test
    void 공휴일에_출석하면_예외가_발생한다() {
        LocalDate holiday = LocalDate.of(2024, 12, 25);

        assertThatThrownBy(() -> new AttendancePolicy().validateIsHolidays(holiday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 공휴일에는 등교할 수 없습니다.");
    }
}
