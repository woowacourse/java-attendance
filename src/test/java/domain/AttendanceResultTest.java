package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceResultTest {

    @Test
    @DisplayName("attendanceTime이 null이면 결석으로 간주한다.")
    void findAttendanceResult_WhenAttendanceTimeIsNull() {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        LocalTime localTime = null;
        // when
        AttendanceResult attendanceResult = AttendanceResult.findAttendanceResult(localDate, localTime);
        // then
        assertThat(attendanceResult).isEqualTo(AttendanceResult.ABSENCE);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("월요일인 경우 13시를 기준으로 출석을 판단한다.")
    void findAttendanceResult_WhenMonday(LocalTime attendanceTime, AttendanceResult expected) {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 2);
        // when
        AttendanceResult attendanceResult = AttendanceResult.findAttendanceResult(localDate, attendanceTime);
        // then
        assertThat(attendanceResult).isEqualTo(expected);
    }

    public static Stream<Arguments> findAttendanceResult_WhenMonday() {
        return Stream.of(
                Arguments.of(LocalTime.of(13, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalTime.of(13, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalTime.of(13, 6), AttendanceResult.LATE),
                Arguments.of(LocalTime.of(13, 30), AttendanceResult.LATE),
                Arguments.of(LocalTime.of(13, 31), AttendanceResult.ABSENCE)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("월요일외 경우 10시를 기준으로 출석을 판단한다.")
    void findAttendanceResult_WhenNotMonday(LocalTime attendanceTime, AttendanceResult expected) {
        // given
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        // when
        AttendanceResult attendanceResult = AttendanceResult.findAttendanceResult(localDate, attendanceTime);
        // then
        assertThat(attendanceResult).isEqualTo(expected);
    }

    public static Stream<Arguments> findAttendanceResult_WhenNotMonday() {
        return Stream.of(
                Arguments.of(LocalTime.of(10, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalTime.of(10, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalTime.of(10, 6), AttendanceResult.LATE),
                Arguments.of(LocalTime.of(10, 30), AttendanceResult.LATE),
                Arguments.of(LocalTime.of(10, 31), AttendanceResult.ABSENCE)
        );
    }
}