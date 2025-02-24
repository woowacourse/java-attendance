package domain;

import java.time.LocalDate;
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
                new Attendance(Map.of(new Date(LocalDate.of(2024, 12, 13)), new Time(10, 0)))
        );

        Map<String, CrewAttendance> attendanceRecords = new HashMap<>();
        attendanceRecords.put(crewName, crewAttendance);

        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(attendanceRecords);

        // when
        CrewAttendance foundCrewAttendance = crewAttendanceRepository.findByName(crewName);

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
                new Attendance(Map.of(new Date(LocalDate.of(2024, 12, 13)), new Time(10, 0)))
        );
        CrewAttendance crewAttendance2 = new CrewAttendance(
                crew2,
                new Attendance(Map.of(new Date(LocalDate.of(2024, 12, 14)), new Time(10, 0)))
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

    @Test
    void 존재하지_않는_크루는_찾지_못한다() {
        // given
        CrewAttendanceRepository crewAttendanceRepository = new CrewAttendanceRepository(new HashMap<>());

        String crewName = "없는이름";

        // when & then
        Assertions.assertThatThrownBy(() -> crewAttendanceRepository.findByName(crewName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이름의 출석 정보가 없습니다.");
    }
}
