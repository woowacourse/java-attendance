package attendance.domain;

import static attendance.domain.AttendanceType.ABSENCE;
import static attendance.domain.AttendanceType.ATTENDANCE;
import static attendance.domain.AttendanceType.LATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendancePolicyTest {
    @DisplayName("요일과_등교_시간에_따라서_출석_상태를_반환할_수_있다")
    @MethodSource("returnDateAndAttendanceTimeAndExpectedType")
    @ParameterizedTest
    void checkAttendanceType(LocalDate attendanceDate, LocalTime attendanceTime, AttendanceType expected) {
        //when
        AttendanceType result = AttendancePolicy.checkAttendanceType(attendanceDate, attendanceTime);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> returnDateAndAttendanceTimeAndExpectedType() {
        LocalDate TUESDAY = LocalDate.of(2024, 12, 26);
        LocalDate MONDAY = LocalDate.of(2024, 12, 23);
        return Stream.of(Arguments.arguments(MONDAY, LocalTime.of(12, 30), ATTENDANCE),
                Arguments.arguments(MONDAY, LocalTime.of(13, 5), ATTENDANCE),
                Arguments.arguments(MONDAY, LocalTime.of(13, 6), LATE),
                Arguments.arguments(MONDAY, LocalTime.of(13, 30), LATE),
                Arguments.arguments(MONDAY, LocalTime.of(13, 31), ABSENCE),
                Arguments.arguments(TUESDAY, LocalTime.of(8, 12), ATTENDANCE),
                Arguments.arguments(TUESDAY, LocalTime.of(10, 5), ATTENDANCE),
                Arguments.arguments(TUESDAY, LocalTime.of(10, 6), LATE),
                Arguments.arguments(TUESDAY, LocalTime.of(10, 30), LATE),
                Arguments.arguments(TUESDAY, LocalTime.of(10, 31), ABSENCE));
    }

    @DisplayName("날짜가_주말_또는_공휴일이면_예외를_던진다")
    @MethodSource("returnWeekendOrHoliday")
    @ParameterizedTest
    void should_ThrowException_WhenDateIsWeekendOrHoliday(LocalDate date) {
        //when
        //then
        assertThatThrownBy(() -> AttendancePolicy.checkNotWeekendAndHoliday(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등교일이 아닙니다");
    }

    private static Stream<Arguments> returnWeekendOrHoliday() {
        return Stream.of(Arguments.arguments(LocalDate.of(2024, 12, 21)),
                Arguments.arguments(LocalDate.of(2024, 12, 22)),
                Arguments.arguments(LocalDate.of(2024, 12, 25)));
    }
}
