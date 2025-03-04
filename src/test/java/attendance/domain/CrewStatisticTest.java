package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewStatisticTest {
    private Attendances crewAttendances;

    @BeforeEach
    void setUp() {
        crewAttendances = new Attendances(
                List.of(
                        new Attendance(new CrewName("빙봉"), new AttendanceDate("17"), new AttendanceTime("10:00")),
                        new Attendance(new CrewName("빙봉"), new AttendanceDate("18"), new AttendanceTime("10:07")),
                        new Attendance(new CrewName("빙봉"), new AttendanceDate("19"), new AttendanceTime("10:09")),
                        new Attendance(new CrewName("빙봉"), new AttendanceDate("20"), new AttendanceTime("10:31")),
                        new Attendance(new CrewName("빙봉"), new AttendanceDate("21"), new AttendanceTime("10:06"))
                )
        );
    }

    @DisplayName("정상: 크루 출석 기록을 기반으로 출결 횟수 산출 확인")
    @Test
    void successExecutionCheckAttendancesCount() {
        CrewStatistic crewStatistic = new CrewStatistic(new CrewName("빙봉"));
        crewStatistic.checkAttendanceStatistic(crewAttendances);

        List<Integer> attendanceCounts = List.of(
                crewStatistic.getCrewSafeCount(),
                crewStatistic.getCrewLateCount(),
                crewStatistic.getCrewAbsentCount()
        );
        assertThat(attendanceCounts).containsExactly(1, 3, 1);
    }

    @DisplayName("정상: 크루 출석 기록을 기반으로 출결 징계 산출 확인")
    @Test
    void successExecutionCheckPenalty() {
        CrewStatistic crewStatistic = new CrewStatistic(new CrewName("빙봉"));
        crewStatistic.checkAttendanceStatistic(crewAttendances);

        assertThat(crewStatistic.getCrewPenalty()).isEqualTo("경고");
    }
}
