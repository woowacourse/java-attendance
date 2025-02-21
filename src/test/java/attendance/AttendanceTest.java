package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceArgumentException;
import attendance.repository.AttendanceFileRepository;
import attendance.service.AttendanceManagerService;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {

    @AfterEach
    void afterTest() {
        AttendanceManager.initiateInstance();
    }

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
        AttendanceManagerService attendanceManagerService = new AttendanceManagerService(
                AttendanceManager.getInstance(), new AttendanceFileRepository("/test.csv"));
        assertThat(
                attendanceManagerService.attendanceResult("투다", LocalDate.of(2024, 12, 13))
        ).contains("(출석)");
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 완료 테스트")
    void testAttendances(String src, String name, LocalDate localDate, String attendanceStatus) {
        AttendanceManagerService attendanceManagerService = new AttendanceManagerService(
                AttendanceManager.getInstance(), new AttendanceFileRepository(src));
        assertThat(
                attendanceManagerService.attendanceResult(name, localDate)
        ).contains(attendanceStatus);
    }

    @Test
    @DisplayName("주말에 출석시 예외 발생 테스트")
    void testWeekend() {
        assertThatThrownBy(() -> new AttendanceManagerService(AttendanceManager.getInstance(),
                new AttendanceFileRepository("/testWeekend.csv")))
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("등교 외 출석시 예외 발생 테스트")
    void testSchoolStartTime() {
        assertThatThrownBy(() -> new AttendanceManagerService(AttendanceManager.getInstance(),
                new AttendanceFileRepository("/testSchoolTime.csv")))
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("등교시간에만 출석 가능합니다.");
    }

    @Test
    @DisplayName("입력 날짜가 유효하지 않을시 예외 발생 테스트")
    void testInvalidDate() {
        assertThatThrownBy(() -> new AttendanceManagerService(AttendanceManager.getInstance(),
                new AttendanceFileRepository("/testInvalidDate.csv")))
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("유효하지 않은 날짜입니다.");
    }

    @Test
    @DisplayName("이미 동일한 날짜에 출석했을 시 예외 발생 테스트")
    void duplicateAttendanceTest() {
        assertThatThrownBy(() -> new AttendanceManagerService(AttendanceManager.getInstance(),
                new AttendanceFileRepository("/testDuplicateNickname.csv")))
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("이미 출석되었습니다.");
    }

    @Test
    @DisplayName("유효하지 않은 닉네임인 경우 예외 발생 테스트")
    void testNicknameInvalid() {
        assertThatThrownBy(() -> new AttendanceManagerService(AttendanceManager.getInstance(),
                new AttendanceFileRepository("/testInvalidNickname.csv")))
                .isInstanceOf(AttendanceArgumentException.class)
                .hasMessageContaining("닉네임은 공백일 수 없습니다.");
    }
}
