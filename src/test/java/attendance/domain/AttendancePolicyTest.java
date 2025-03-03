package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendancePolicyTest {
    @DisplayName("주어진_요일과_출석_시간을_기반으로_출석_상태를_반환할_수_있다")
    @CsvSource(value = {
            "MONDAY,13:05,ATTENDANCE",
            "MONDAY,13:06,LATE",
            "MONDAY,13:30,LATE",
            "MONDAY,13:31,ABSENCE",
            "WEDNESDAY,10:05,ATTENDANCE",
            "TUESDAY,10:06,LATE",
            "THURSDAY,10:30,LATE",
            "FRIDAY,10:31,ABSENCE"
    }, delimiterString = ",")
    @ParameterizedTest
    void calculateAttendanceStatus(DayOfWeek day, LocalTime time, AttendanceStatus expected) {
        //when
        AttendanceStatus result = AttendancePolicy.calculateAttendanceStatus(day, time);

        //then
        assertThat(result).isEqualTo(expected);
    }
}
