package domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeTest {

    @DisplayName("LocalTime 기반 AttendanceTime 객체 생성 테스트")
    @ParameterizedTest
    @MethodSource("provideCampusRunningTime")
    void generateAttendanceTimeTest(LocalTime openTime) {
        assertDoesNotThrow(() -> new AttendanceTime(openTime));
    }

    private static Stream<Arguments> provideCampusRunningTime() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(8, 0)),
                Arguments.arguments(LocalTime.of(23, 0))
        );
    }

    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외 발생 테스트")
    @ParameterizedTest
    @MethodSource("provideCampusNotRunningTime")
    void campusNotRunningTest(LocalTime closeTime) {
        assertThatThrownBy(() -> new AttendanceTime(closeTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideCampusNotRunningTime() {
        return Stream.of(
                Arguments.arguments(LocalTime.of(7, 59)),
                Arguments.arguments(LocalTime.of(23, 1))
        );
    }

    @DisplayName("캠퍼스 등교일을 기반으로 지각 여부 판단")
    @ParameterizedTest
    @MethodSource("provideAttendanceDateAndLateStatus")
    void checkLateStatusTest(AttendanceDate attendanceDate, AttendanceTime attendanceTime, boolean expected) {
        Assertions.assertThat(attendanceTime.isAfterLateTime(attendanceDate))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideAttendanceDateAndLateStatus() {
        return Stream.of(
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 5)), false),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 6)), true),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 5)), false),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 6)), true)
        );
    }

    @DisplayName("캠퍼스 등교일을 기반으로 결석 여부 판단")
    @ParameterizedTest
    @MethodSource("provideAttendanceDateAndAbsentStatus")
    void checkAbsentStatusTest(AttendanceDate attendanceDate, AttendanceTime attendanceTime, boolean expected) {
        Assertions.assertThat(attendanceTime.isAfterAbsentTime(attendanceDate))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideAttendanceDateAndAbsentStatus() {
        return Stream.of(
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 30)), false),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 31)), true),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 30)), false),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 31)), true)
        );
    }
}
