package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AttendancesTest {

    private static Stream<Arguments> provideCrewAttendances() {
        return Stream.of(
                Arguments.arguments("짱수", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 5)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 10)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 4)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 0))
                ), 3, 1, 1),
                Arguments.arguments("빙봉", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 30)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 15)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 9, 56)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 40))
                ), 2, 2, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCrewAttendances")
    void 크루의_출석_상태별_횟수를_조회한다(String name, List<Attendance> attendanceLogs,
                             int attendanceCount, int lateCount, int absentCount) {

        Attendances attendances = new Attendances(Map.of(name, attendanceLogs));

        assertThat(attendances.calculateAttendanceCount(name)).isEqualTo(attendanceCount);
        assertThat(attendances.calculateLateCount(name)).isEqualTo(lateCount);
        assertThat(attendances.calculateAbsentCount(name)).isEqualTo(absentCount);
    }
}
