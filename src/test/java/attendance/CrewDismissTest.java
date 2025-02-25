package attendance;

import attendance.domain.AttendanceDismissStatus;
import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceStatuses;
import attendance.domain.CrewAttendanceHistory;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CrewDismissTest {

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
                Arguments.arguments(
                        "/testCrewDismissLate.csv",
                        List.of("몽이", "투다"),
                        List.of(AttendanceDismissStatus.NEED_MEETING, AttendanceDismissStatus.NEED_MEETING)
                ),
                Arguments.arguments(
                        "/testCrewDismissNickname.csv",
                        List.of("몽이", "투다"),
                        List.of(AttendanceDismissStatus.NEED_MEETING, AttendanceDismissStatus.NEED_MEETING)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("제적 대상자 출력 테스트")
    void testAttendances(String src, List<String> nicknames, List<AttendanceDismissStatus> attendanceStatus) {
        AttendanceManager attendanceManager = new AttendanceManager(
                new AttendanceReader(src).loadAttendanceLinesFromAttendanceFile());
        List<CrewAttendanceHistory> attendanceHistories = attendanceManager.crewDismissHistory();

        for (int i = 0; i < nicknames.size(); i++) {
            Assertions.assertThat(attendanceManager.crewDismissHistory());
            CrewAttendanceHistory crewAttendanceHistory = attendanceHistories.get(i);
            String nickname = nicknames.get(i);
            AttendanceStatuses attendances = crewAttendanceHistory.attendanceStatuses();
            AttendanceDismissStatus attendanceDismissStatus = attendances.calculateAttendanceDismiss();
            Assertions.assertThat(crewAttendanceHistory.nickname()).isEqualTo(nickname);
            Assertions.assertThat(attendanceDismissStatus).isEqualTo(attendanceStatus.get(i));
        }
    }
}
