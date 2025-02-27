package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59, ATTENDANCE", "2025, 2, 24, 13, 6, LATE", "2025, 2, 24, 13, 31, ABSENCE",
            "2025, 2, 25, 9, 59, ATTENDANCE", "2025, 2, 25, 10, 6, LATE", "2025, 2, 25, 10, 31, ABSENCE",
            "2025, 2, 26, 9, 59, ATTENDANCE", "2025, 2, 26, 10, 6, LATE", "2025, 2, 26, 10, 31, ABSENCE",
            "2025, 2, 27, 9, 59, ATTENDANCE", "2025, 2, 27, 10, 6, LATE", "2025, 2, 27, 10, 31, ABSENCE",
            "2025, 2, 28, 9, 59, ATTENDANCE", "2025, 2, 28, 10, 6, LATE", "2025, 2, 28, 10, 31, ABSENCE"})
    public void 출석_시간_생성(int year, int month, int day, int hour, int minute, AttendanceStatus attendanceStatus) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour,minute);

        //when
        AttendanceTime attendanceTime = new AttendanceTime(localDateTime);

        //then
        Assertions.assertThat(attendanceTime.getAttendanceTime()).isEqualTo(localDateTime);
        Assertions.assertThat(attendanceTime.getAttendanceStatus()).isEqualTo(attendanceStatus);
    }
}
