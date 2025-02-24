package attendance.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceTest {

    @ParameterizedTest
    @MethodSource
    void 날짜와_시간으로_출석을_생성한다(LocalDateTime dateTime, AttendanceStateType excepted) {
        // when
        Attendance result = new Attendance(dateTime);

        // than
        assertThat(result.getDateTime()).isEqualTo(dateTime);
        assertThat(result.getState()).isEqualTo(excepted);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-05T10:00:00, 2024-12-05, true",
            "2024-12-05T10:00:00, 2024-12-06, false"
    })
    void 동일_날짜_여부를_확인한다(LocalDateTime dateTime, LocalDate compare, boolean expected) {
        // given
        Attendance attendance = new Attendance(dateTime);

        // then
        assertThat(attendance.isSameDate(compare))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void 이미_출석_여부를_검사한다(LocalDateTime dateTime, boolean expected) {
        // given
        Attendance attendance = new Attendance(dateTime);

        // then
        assertThat(attendance.isAlreadyChecked())
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    void 출석_상태가_동일한지_검사한다(AttendanceStateType state, boolean expected) {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        Attendance attendance = new Attendance(dateTime);

        // when
        boolean result = attendance.hasState(state);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-07T10:00, 2024-12-06T10:00, 1",
            "2024-12-07T10:00, 2024-12-08T10:00, -1",
            "2024-12-07T10:00, 2024-12-07T10:00, 0"
    })
    void 출석의_시간을_비교해_반환한다(LocalDateTime firstDateTime, LocalDateTime secondDateTime, int excepted) {
        // given
        Attendance firstAttendance = new Attendance(firstDateTime);
        Attendance secondAttendance = new Attendance(secondDateTime);

        // when
        int result = firstAttendance.compareTo(secondAttendance);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-03, false",
            "2024-12-05, true"
    })
    void 날짜를_비교해_반환한다(LocalDate compare, boolean excepted) {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 4, 13, 0);

        Attendance attendance = new Attendance(dateTime);

        // when
        boolean result = attendance.isBefore(compare);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    static Stream<Arguments> 날짜와_시간으로_출석을_생성한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 0), AttendanceStateType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 6), AttendanceStateType.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 31), AttendanceStateType.EXPULSION)
        );
    }

    static Stream<Arguments> 이미_출석_여부를_검사한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.of(2024, 12, 3), LocalTime.MAX), false),
                Arguments.of(LocalDateTime.of(LocalDate.of(2024, 12, 3), LocalTime.MIDNIGHT), true)
        );
    }

    static Stream<Arguments> 출석_상태가_동일한지_검사한다() {
        return Stream.of(
                Arguments.of(AttendanceStateType.ATTENDANCE, true),
                Arguments.of(AttendanceStateType.EXPULSION, false)
        );
    }
}
