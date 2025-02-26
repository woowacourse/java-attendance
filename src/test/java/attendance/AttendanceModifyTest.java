package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceModifyTest {

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
                Arguments.arguments(
                        "/testModify.csv",
                        "투다",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 31),
                        AttendanceStatus.ABSENCE.getStatus()
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "체체",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 15),
                        AttendanceStatus.LATE.getStatus()
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "꾹이",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 15),
                        AttendanceStatus.LATE.getStatus()
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "비타",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 4),
                        AttendanceStatus.ATTENDANCE.getStatus()
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "듀이",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 41),
                        AttendanceStatus.ABSENCE.getStatus()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 데이터 수정 테스트")
    void testAttendances(String src, String name, LocalDate modifyDate, LocalTime afterModifyTime,
                         String modifyResult) {
        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader(src).loadAttendanceLinesFromAttendanceFile());
        attendanceManager.modifyAttendance(name, modifyDate, afterModifyTime);

        assertThat(
                attendanceManager.findAttendance(name, modifyDate)
                        .attendanceStatus()
                        .getStatus()
        ).contains(modifyResult);
    }
}
