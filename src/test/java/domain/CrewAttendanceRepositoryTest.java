package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CrewAttendanceRepositoryTest {

    @Test
    void 출석을_찾는다() {
        // given
        String crewName = "이름";
        Crew crew = new Crew(crewName);
        CrewAttendance crewAttendance = new CrewAttendance(
                crew,
                new Attendance(Map.of(new WorkDate(2024, 12, 13), new WorkTime(10, 0)))
        );

        Map<String, CrewAttendance> attendanceRecords = new HashMap<>();
        attendanceRecords.put(crewName, crewAttendance);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(attendanceRecords);

        // when
        CrewAttendance foundCrewAttendance = crewAttendanceRepository.findByName(crewName).get();

        // then
        assertThat(crewAttendance)
                .isEqualTo(foundCrewAttendance);
    }

    @Test
    void 패널티가_있는_크루만_찾고_정렬한다() {
        // given
        String crewName1 = "이름1";
        String crewName2 = "이름2";
        String crewName3 = "이름3";

        Crew crew1 = new Crew(crewName1);
        Crew crew2 = new Crew(crewName2);
        Crew crew3 = new Crew(crewName3);

        CrewAttendance crewAttendance1 = new CrewAttendance(
                crew1,
                new Attendance(Map.of(
                        new WorkDate(2024, 12, 10), new WorkTime(10, 35),
                        new WorkDate(2024, 12, 11), new WorkTime(10, 35),
                        new WorkDate(2024, 12, 12), new WorkTime(10, 6),
                        new WorkDate(2024, 12, 13), new WorkTime(10, 0)
                ))
        );

        CrewAttendance crewAttendance2 = new CrewAttendance(
                crew2,
                new Attendance(Map.of(
                        new WorkDate(2024, 12, 10), new WorkTime(10, 0),
                        new WorkDate(2024, 12, 11), new WorkTime(10, 6)
                ))
        );

        CrewAttendance crewAttendance3 = new CrewAttendance(
                crew3,
                new Attendance(Map.of(
                        new WorkDate(2024, 12, 10), new WorkTime(10, 35),
                        new WorkDate(2024, 12, 11), new WorkTime(10, 35),
                        new WorkDate(2024, 12, 12), new WorkTime(9, 0),
                        new WorkDate(2024, 12, 13), new WorkTime(9, 0)
                ))
        );

        Map<String, CrewAttendance> attendanceRecords = new HashMap<>();
        attendanceRecords.put(crewName1, crewAttendance1);
        attendanceRecords.put(crewName2, crewAttendance2);
        attendanceRecords.put(crewName3, crewAttendance3);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(attendanceRecords);

        // when
        List<CrewAttendance> crewAttendances = crewAttendanceRepository.findAllOrderByAbsence();

        // then
        assertThat(crewAttendances)
                .containsExactly(crewAttendance1, crewAttendance3);
    }
}
