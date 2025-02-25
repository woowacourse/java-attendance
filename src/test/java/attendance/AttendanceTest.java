package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceArgumentException;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
                Arguments.arguments(
                        "/test.csv",
                        "투다",
                        LocalDate.of(2024, 12, 13),
                        AttendanceStatus.ATTENDANCE.getStatus()
                ),
                Arguments.arguments(
                        "/test.csv",
                        "체체",
                        LocalDate.of(2024, 12, 13),
                        AttendanceStatus.LATE.getStatus()
                ),
                Arguments.arguments(
                        "/test.csv",
                        "꾹이",
                        LocalDate.of(2024, 12, 13),
                        AttendanceStatus.ABSENCE.getStatus()
                ),
                Arguments.arguments(
                        "/test.csv",
                        "꾹이",
                        LocalDate.of(2024, 12, 16),
                        AttendanceStatus.LATE.getStatus()
                ),
                Arguments.arguments(
                        "/test.csv",
                        "꾹이",
                        LocalDate.of(2024, 12, 17),
                        AttendanceStatus.ABSENCE.getStatus()
                )
        );
    }

    @Test
    @DisplayName("출석 데이터 불러오기 테스트")
    void testLoadAttendance() {
        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader("/test.csv").loadAttendanceLinesFromAttendanceFile());
        assertThat(
                attendanceManager.findAttendance("투다", LocalDate.of(2024, 12, 13))
                        .attendanceStatus()
        ).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 완료 테스트")
    void testAttendances(String src, String name, LocalDate localDate, String attendanceStatus) {
        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader(src).loadAttendanceLinesFromAttendanceFile());
        assertThat(
                attendanceManager.findAttendance(name, localDate)
                        .attendanceStatus().getStatus()
        ).contains(attendanceStatus);
    }

    @Test
    @DisplayName("주말에 출석시 예외 발생 테스트")
    void testWeekend() {
        AttendanceReader attendanceReader = new AttendanceReader("/testWeekend.csv");
        assertThatThrownBy(() ->
                new AttendanceManager(attendanceReader.loadAttendanceLinesFromAttendanceFile())
        )
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("등교 외 출석시 예외 발생 테스트")
    void testSchoolStartTime() {
        AttendanceReader attendanceReader = new AttendanceReader("/testSchoolTime.csv");
        assertThatThrownBy(() ->
                new AttendanceManager(attendanceReader.loadAttendanceLinesFromAttendanceFile())
        )
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("등교시간에만 출석 가능합니다.");
    }

    @Test
    @DisplayName("입력 날짜가 유효하지 않을시 예외 발생 테스트")
    void testInvalidDate() {
        AttendanceReader attendanceReader = new AttendanceReader("/testInvalidDate.csv");
        assertThatThrownBy(() ->
                new AttendanceManager(attendanceReader.loadAttendanceLinesFromAttendanceFile())
        ).isInstanceOf(AttendanceArgumentException.class);
    }

    @Test
    @DisplayName("이미 동일한 날짜에 출석했을 시 예외 발생 테스트")
    void duplicateAttendanceTest() {
        AttendanceReader attendanceReader = new AttendanceReader("/testDuplicateNickname.csv");
        assertThatThrownBy(() ->
                new AttendanceManager(attendanceReader.loadAttendanceLinesFromAttendanceFile())
        )
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("이미 출석되었습니다.");
    }

    @Test
    @DisplayName("유효하지 않은 닉네임인 경우 예외 발생 테스트")
    void testNicknameInvalid() {
        AttendanceReader attendanceReader = new AttendanceReader("/testInvalidNickname.csv");
        assertThatThrownBy(() ->
                new AttendanceManager(attendanceReader.loadAttendanceLinesFromAttendanceFile())
        ).isInstanceOf(AttendanceArgumentException.class);
    }
}
