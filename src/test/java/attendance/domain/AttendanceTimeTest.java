package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59", "2025, 2, 24, 13, 6", "2025, 2, 24, 13, 31",
            "2025, 2, 25, 9, 59", "2025, 2, 25, 10, 6", "2025, 2, 25, 10, 31",
            "2025, 2, 26, 9, 59", "2025, 2, 26, 10, 6", "2025, 2, 26, 10, 31",
            "2025, 2, 27, 9, 59", "2025, 2, 27, 10, 6", "2025, 2, 27, 10, 31",
            "2025, 2, 28, 9, 59", "2025, 2, 28, 10, 6", "2025, 2, 28, 10, 31"})
    void 출석_시간_생성(int year, int month, int day, int hour, int minute) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        //when & then
        assertDoesNotThrow(() -> new AttendanceTime(localDateTime));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59, true", "2025, 2, 24, 13, 6, false", "2025, 2, 24, 13, 31, false",
            "2025, 2, 25, 9, 59, true", "2025, 2, 25, 10, 6, false", "2025, 2, 25, 10, 31, false",
            "2025, 2, 26, 9, 59, true", "2025, 2, 26, 10, 6, false", "2025, 2, 26, 10, 31, false",
            "2025, 2, 27, 9, 59, true", "2025, 2, 27, 10, 6, false", "2025, 2, 27, 10, 31, false",
            "2025, 2, 28, 9, 59, true", "2025, 2, 28, 10, 6, false", "2025, 2, 28, 10, 31, false"
    })
    void 출석_시간에_따른_출석_상태(int year, int month, int day, int hour, int minute, boolean expectedResult) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        AttendanceTime attendanceTime = new AttendanceTime(localDateTime);
        //when & then
        Assertions.assertThat(attendanceTime.isAttendance()).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59, false", "2025, 2, 24, 13, 6, true", "2025, 2, 24, 13, 31, false",
            "2025, 2, 25, 9, 59, false", "2025, 2, 25, 10, 6, true", "2025, 2, 25, 10, 31, false",
            "2025, 2, 26, 9, 59, false", "2025, 2, 26, 10, 6, true", "2025, 2, 26, 10, 31, false",
            "2025, 2, 27, 9, 59, false", "2025, 2, 27, 10, 6, true", "2025, 2, 27, 10, 31, false",
            "2025, 2, 28, 9, 59, false", "2025, 2, 28, 10, 6, true", "2025, 2, 28, 10, 31, false"
    })
    void 출석_시간에_따른_지각_상태(int year, int month, int day, int hour, int minute, boolean expectedResult) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        AttendanceTime attendanceTime = new AttendanceTime(localDateTime);
        //when & then
        Assertions.assertThat(attendanceTime.isLate()).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59, false", "2025, 2, 24, 13, 6, false", "2025, 2, 24, 13, 31, true",
            "2025, 2, 25, 9, 59, false", "2025, 2, 25, 10, 6, false", "2025, 2, 25, 10, 31, true",
            "2025, 2, 26, 9, 59, false", "2025, 2, 26, 10, 6, false", "2025, 2, 26, 10, 31, true",
            "2025, 2, 27, 9, 59, false", "2025, 2, 27, 10, 6, false", "2025, 2, 27, 10, 31, true",
            "2025, 2, 28, 9, 59, false", "2025, 2, 28, 10, 6, false", "2025, 2, 28, 10, 31, true"
    })
    void 출석_시간에_따른_결석_상태(int year, int month, int day, int hour, int minute, boolean expectedResult) {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);

        AttendanceTime attendanceTime = new AttendanceTime(localDateTime);
        //when & then
        Assertions.assertThat(attendanceTime.isAbsence()).isEqualTo(expectedResult);
    }
}
