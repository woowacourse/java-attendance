package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CrewStatisticTest {
    private static final int ATTENDANCE_TOTAL_DAYS = 15;

    private final Crews crews = new Crews();
    private final Attendances attendances = new Attendances();
    private final Crew crew = new Crew("쿠키");
    private CrewStatistic crewStatistic;

    void setUp(List<List<String>> attendanceRecords) {
        crews.initCrews(attendanceRecords);
        attendances.initAttendances(crews, attendanceRecords);

        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);

        crewStatistic = new CrewStatistic(crew, crewAttendances);
        crewStatistic.initCrewsStatus();
        crewStatistic.calculatePenalty();
    }

    @DisplayName("기능: 크루별 출석 기록 및 출결 횟수 정보 확인")
    @MethodSource("provideAttendanceRecords")
    @ParameterizedTest
    void checkCrewAttendanceStatistics(List<List<String>> attendanceRecords) {
        setUp(attendanceRecords);

        assertThat(crewStatistic.crewAttendanceHistoryInfo().size()).isEqualTo(ATTENDANCE_TOTAL_DAYS);

        String safeCount = String.valueOf(5);
        String lateCount = String.valueOf(3);
        String absentCount = String.valueOf(ATTENDANCE_TOTAL_DAYS - attendanceRecords.size() + 1);
        List<String> expectedStatisticInfo = List.of(safeCount, lateCount, absentCount, "제적");

        assertThat(crewStatistic.crewStatisticStatusInfo()).isEqualTo(expectedStatisticInfo);
    }

    @DisplayName("기능: 제적 위험자 정보 확인")
    @MethodSource("provideAttendanceRecords")
    @ParameterizedTest
    void test(List<List<String>> attendanceRecords) {
        setUp(attendanceRecords);

        String absentCount = String.valueOf(ATTENDANCE_TOTAL_DAYS - attendanceRecords.size() + 1);
        String lateCount = String.valueOf(3);
        List<String> expectedExpelInfo = List.of("쿠키", absentCount, lateCount, "제적");

        assertThat(crewStatistic.crewExpelExpectedInfo()).isEqualTo(expectedExpelInfo);
    }

    private static Stream<Arguments> provideAttendanceRecords() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                List.of("쿠키", "2025-02-19 10:08"),
                                List.of("쿠키", "2025-02-18 10:00"),
                                List.of("쿠키", "2025-02-17 13:03"),
                                List.of("쿠키", "2025-02-14 10:02"),
                                List.of("쿠키", "2025-02-13 10:07"),
                                List.of("쿠키", "2025-02-12 10:01"),
                                List.of("쿠키", "2025-02-11 10:00"),
                                List.of("쿠키", "2025-02-10 13:09")
                        )
                )
        );
    }
}
