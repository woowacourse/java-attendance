package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceManager;
import attendance.repository.AttendanceFileRepository;
import attendance.service.AttendanceManagerService;
import attendance.service.CrewDismissService;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CrewDismissTest {

    @AfterEach
    void afterTest() {
        AttendanceManager.initiateInstance();
    }

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
                Arguments.arguments(
                        "/testCrewDismiss.csv",
                        "제적 위험자 조회 결과\n"
                                + "- 몽이: 결석 20회, 지각 0회 (제적)\n"
                                + "- 투다: 결석 20회, 지각 0회 (제적)\n"
                                + "- 체체: 결석 20회, 지각 1회 (제적)\n"
                                + "- 꾹이: 결석 19회, 지각 1회 (제적)"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 데이터 테스트")
    void testAttendances(String src, String formattedResult) {
        AttendanceManager attendanceManager = AttendanceManager.getInstance();
        AttendanceManagerService attendanceManagerService = new AttendanceManagerService(attendanceManager,
                new AttendanceFileRepository(src));
        CrewDismissService crewDismissService = new CrewDismissService(attendanceManager);
        assertThat(
                crewDismissService.formattingCrewDismiss()
        ).contains(formattedResult);
    }
}
