package domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {

    @DisplayName("출석 날짜와 시간 기반 출석 기록 생성 테스트")
    @Test
    void generateAttendanceTest() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 2));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 0));

        assertDoesNotThrow(() -> new Attendance(attendanceDate, attendanceTime));
    }

    @DisplayName("출석 시간 수정 테스트")
    @Test
    void editAttendanceTimeTest() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 3));
        AttendanceTime oldTime = new AttendanceTime(LocalTime.of(10, 35));
        AttendanceTime newTime = new AttendanceTime(LocalTime.of(9, 55));

        Attendance attendance = new Attendance(attendanceDate, oldTime);
        attendance.editTime(newTime);

        Assertions.assertThat(attendance.isLate()).isEqualTo(false);
    }

    @ParameterizedTest
    @DisplayName("출석 확인 테스트")
    @MethodSource("provideAttendances")
    void checkAttendTest(Attendance attendance, boolean expected) {
        Assertions.assertThat(attendance.isAttended()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideAttendances() {
        Attendance attend = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(9, 55)));
        Attendance late = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 6)));
        Attendance absent = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 35)));

        return Stream.of(
                Arguments.arguments(attend, true),
                Arguments.arguments(late, false),
                Arguments.arguments(absent, false)
        );
    }

    @ParameterizedTest
    @DisplayName("지각 확인 테스트")
    @MethodSource("provideLateAttendances")
    void checkLateTest(Attendance attendance, boolean expected) {
        Assertions.assertThat(attendance.isLate()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideLateAttendances() {
        Attendance attend = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(9, 55)));
        Attendance late = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 6)));
        Attendance absent = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 35)));

        return Stream.of(
                Arguments.arguments(attend, false),
                Arguments.arguments(late, true),
                Arguments.arguments(absent, false)
        );
    }

    @ParameterizedTest
    @DisplayName("결석 확인 테스트")
    @MethodSource("provideAbsentAttendances")
    void checkAbsentTest(Attendance attendance, boolean expected) {
        Assertions.assertThat(attendance.isUnattendedOrNoShow()).isEqualTo(expected);
    }

    private static Stream<Arguments> provideAbsentAttendances() {
        Attendance attend = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(9, 55)));
        Attendance late = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 6)));
        Attendance absent = new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 35)));

        return Stream.of(
                Arguments.arguments(attend, false),
                Arguments.arguments(late, false),
                Arguments.arguments(absent, true)
        );
    }
}
