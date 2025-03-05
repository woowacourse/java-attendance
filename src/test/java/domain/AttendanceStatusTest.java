package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceStatusTest {


    @ParameterizedTest
    @MethodSource
    @DisplayName("출석, 지각, 결석을 체크한다.")
    void test1(final DayOfWeek dayOfWeek, final LocalTime localTime, final AttendanceStatus expected) {
        //should
        assertThat(AttendanceStatus.findByTime(dayOfWeek, localTime)).isEqualTo(expected);

    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 5), AttendanceStatus.ATTENDANCE),
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 0), AttendanceStatus.ATTENDANCE),
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(12, 59), AttendanceStatus.ATTENDANCE),
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 6), AttendanceStatus.LATE),
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 30), AttendanceStatus.LATE),
                Arguments.of(DayOfWeek.MONDAY, LocalTime.of(13, 31), AttendanceStatus.ABSENCE),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 5), AttendanceStatus.ATTENDANCE),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 0), AttendanceStatus.ATTENDANCE),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(9, 59), AttendanceStatus.ATTENDANCE),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 6), AttendanceStatus.LATE),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 30), AttendanceStatus.LATE),
                Arguments.of(DayOfWeek.TUESDAY, LocalTime.of(10, 31), AttendanceStatus.ABSENCE)
        );
    }
}
