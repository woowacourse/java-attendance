package attendance.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendancePolicyTest {

    @CsvSource(value = {"MONDAY,08:00", "MONDAY,13:00", "MONDAY,13:05",
            "TUESDAY,08:00", "TUESDAY,10:00", "TUESDAY,10:05"}, delimiterString = ",")
    @ParameterizedTest
    void 요일별_출석한_시간이_출석으로_인정되면_true를_반환한다(DayOfWeek dayOfWeek, String time) {
        LocalTime attendanceTime = LocalTime.parse(time);

        assertThat(AttendancePolicy.isCheckIn(dayOfWeek, attendanceTime)).isTrue();
    }

    @CsvSource(value = {"MONDAY,13:06", "TUESDAY,10:06"}, delimiterString = ",")
    @ParameterizedTest
    void 요일별_출석한_시간이_지각이면_true를_반환한다(DayOfWeek dayOfWeek, String time) {
        LocalTime attendanceTime = LocalTime.parse(time);

        assertThat(AttendancePolicy.isLate(dayOfWeek, attendanceTime)).isTrue();
    }
}
