package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 테스트")
class AttendanceTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("날짜와 시간으로 출석을 생성한다")
    void createAttendanceFromDateTime(LocalDateTime dateTime, AttendanceState state) {
        // when
        Attendance result = Attendance.createFromDateTime(dateTime);

        // then
        assertAll(
                () -> assertThat(result.getDateTime()).isEqualTo(dateTime),
                () -> assertThat(result.getState()).isEqualTo(state)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "2025-03-05T10:00, 2025-03-05, true",
            "2025-03-05T10:00, 2025-03-06, false",
    })
    @DisplayName("동일한 날짜인지 판단해 반환한다")
    void isSameDate(LocalDateTime dateTime, LocalDate date, boolean excepted) {
        // given
        Attendance attendance = Attendance.createFromDateTime(dateTime);

        // when
        boolean result = attendance.isSameDate(date);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("기본 시간인지 판단해 반환한다")
    void isNotDefaultTime(LocalDateTime dateTime, boolean excepted) {
        // given
        Attendance attendance = Attendance.createFromDateTime(dateTime);

        // when
        boolean result = attendance.isNotDefaultTime();

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @ParameterizedTest
    @CsvSource({
            "2025-03-05T10:00, 2025-03-04, false",
            "2025-03-05T10:00, 2025-03-06, true",
    })
    @DisplayName("이전 날짜인지 판단해 반환한다")
    void isDateBefore(LocalDateTime dateTime, LocalDate date, boolean excepted) {
        // given
        Attendance attendance = Attendance.createFromDateTime(dateTime);

        // when
        boolean result = attendance.isDateBefore(date);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("출결 상황이 동일한지 판단해 반환한다")
    void isSameState(LocalDateTime dateTime, AttendanceState state, boolean excepted) {
        // given
        Attendance attendance = Attendance.createFromDateTime(dateTime);

        // when
        boolean result = attendance.isSameState(state);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @ParameterizedTest
    @CsvSource({
            "2025-03-05T10:00, 2025-03-05T10:00, true",
            "2025-03-05T10:00, 2025-03-06T10:00, false",
    })
    @DisplayName("동일한 객체인지 판단해 반환한다")
    void isEqualObject(LocalDateTime dateTime, LocalDateTime otherDateTime, boolean excepted) {
        // given
        Attendance attendance = Attendance.createFromDateTime(dateTime);
        Attendance otherAttendance = Attendance.createFromDateTime(otherDateTime);

        // when
        boolean result = attendance.equals(otherAttendance);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    private static Stream<Arguments> createAttendanceFromDateTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 3, 5, 10, 0, 0), AttendanceState.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2025, 3, 5, 10, 6, 0), AttendanceState.TARDY),
                Arguments.of(LocalDateTime.of(2025, 3, 5, 10, 31, 0), AttendanceState.ABSENCE)
        );
    }

    private static Stream<Arguments> isNotDefaultTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(LocalDate.of(2025, 3, 5), LocalTime.MAX), false),
                Arguments.of(LocalDateTime.of(LocalDate.of(2025, 3, 5), LocalTime.MIN), true)
        );
    }

    private static Stream<Arguments> isSameState() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2025, 3, 5, 10, 0, 0), AttendanceState.ATTENDANCE, true),
                Arguments.of(LocalDateTime.of(2025, 3, 5, 10, 6, 0), AttendanceState.TARDY, true),
                Arguments.of(LocalDateTime.of(2025, 3, 5, 10, 31, 0), AttendanceState.ABSENCE, true)
        );
    }
}
