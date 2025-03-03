package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTypeTest {

    @ParameterizedTest
    @CsvSource({
            "2, 13, 5, 0, 0, SUCCESS",
            "2, 13, 5, 0, 1, LATE",
            "2, 13, 30, 0, 0, LATE",
            "2, 13, 30, 0, 1, ABSENCE",
            "3, 10, 5, 0, 0, SUCCESS",
            "3, 10, 5, 0, 1, LATE",
            "3, 10, 30, 0, 0, LATE",
            "3, 10, 30, 0, 1, ABSENCE",
    })
    void 출석시간으로_출석_결과를_결정한다(int date, int hour, int minute, int second, int nano, AttendanceType expected) {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, date, hour, minute, second, nano);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        //when
        AttendanceType type = AttendanceType.calculateType(attendanceTime);
        //then
        assertThat(type).isEqualTo(expected);
    }

    @ParameterizedTest(name = "결석 기준 시간은 {1}이다")
    @MethodSource("testData")
    void calculateAbsenceStandardTime(LocalDate day, LocalTime expected) {
        //when
        LocalTime actual = AttendanceType.calculateAbsenceStandardTime(day);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testData() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 30)),
                Arguments.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 30))
        );
    }
}
