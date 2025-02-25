package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceStatusTest {

    @DisplayName("출석 기록 기반으로 출석 상태를 도출하는 기능 테스트")
    @ParameterizedTest
    @MethodSource("provideAttendances")
    void generateAttendanceStatusTest(AttendanceDate attendanceDate,
                                      AttendanceTime attendanceTime,
                                      AttendanceStatus expected) {
        assertThat(AttendanceStatus.checkAttendanceStatus(attendanceDate, attendanceTime)).isEqualTo(expected);
    }

    private static Stream<Arguments> provideAttendances() {
        return Stream.of(
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 5)),
                        AttendanceStatus.ATTEND),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 6)),
                        AttendanceStatus.LATE),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 30)),
                        AttendanceStatus.LATE),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 2)),
                        new AttendanceTime(LocalTime.of(13, 31)),
                        AttendanceStatus.UNATTENDED),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 5)),
                        AttendanceStatus.ATTEND),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 6)),
                        AttendanceStatus.LATE),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 30)),
                        AttendanceStatus.LATE),
                Arguments.arguments(new AttendanceDate(LocalDate.of(2024, 12, 3)),
                        new AttendanceTime(LocalTime.of(10, 31)),
                        AttendanceStatus.UNATTENDED)
        );
    }
}
