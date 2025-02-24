package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewAttendanceRepositoryTest {

    @Test
    void 출석을_찾는다() {
        // given
        String crewName = "이름";
        Crew crew = new Crew(crewName);
        CrewAttendance crewAttendance = new CrewAttendance(
                crew,
                new Attendance(Map.of(new Date(2024, 12, 13), new Time(10, 0)))
        );

        Map<String, CrewAttendance> attendanceRecords = new HashMap<>();
        attendanceRecords.put(crewName, crewAttendance);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(attendanceRecords);

        // when
        CrewAttendance foundCrewAttendance = crewAttendanceRepository.findByName(crewName).get();

        // then
        Assertions.assertThat(crewAttendance)
                .isEqualTo(foundCrewAttendance);
    }

    @Test
    void 출석을_모두_찾는다() {
        // given
        String crewName1 = "이름";
        String crewName2 = "이름2";

        Crew crew1 = new Crew(crewName1);
        Crew crew2 = new Crew(crewName2);

        CrewAttendance crewAttendance1 = new CrewAttendance(
                crew1,
                new Attendance(Map.of(new Date(2024, 12, 13), new Time(10, 0)))
        );
        CrewAttendance crewAttendance2 = new CrewAttendance(
                crew2,
                new Attendance(Map.of(new Date(2024, 12, 13), new Time(10, 0)))
        );

        Map<String, CrewAttendance> attendanceRecords = new HashMap<>();
        attendanceRecords.put(crewName1, crewAttendance1);
        attendanceRecords.put(crewName2, crewAttendance2);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(attendanceRecords);

        // when
        List<CrewAttendance> crewAttendances = crewAttendanceRepository.findAll();

        // then
        Assertions.assertThat(crewAttendances)
                .containsExactlyInAnyOrder(crewAttendance1, crewAttendance2);
    }
}
