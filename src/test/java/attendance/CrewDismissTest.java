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
                        "/testCrewDismissLate.csv",
                        "- 몽이: 결석 3회, 지각 3회 (면담)\n\n- 투다: 결석 3회, 지각 0회 (면담)"
                ),
                Arguments.arguments(
                        "/testCrewDismissNickname.csv",
                        "- 몽이: 결석 3회, 지각 3회 (면담)\n\n- 투다: 결석 3회, 지각 3회 (면담)"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("제적 대상자 출력 테스트")
    void testAttendances(String src, String orderedResult) {
        AttendanceManager attendanceManager = AttendanceManager.getInstance();
        AttendanceManagerService attendanceManagerService = new AttendanceManagerService(attendanceManager,
                new AttendanceFileRepository(src));
        CrewDismissService crewDismissService = new CrewDismissService(attendanceManager);
        assertThat(
                crewDismissService.formattingCrewDismiss(attendanceManager.attendancesNicknames())
        ).contains(orderedResult);
    }
}
