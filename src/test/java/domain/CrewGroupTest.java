package domain;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewGroupTest {
    @DisplayName("크루를 검색합니다.")
    @Test
    void searchCrewTest() {
        CrewGroup crewGroup = new CrewGroup();
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 2, 13, 0));
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 3, 15, 0));

        Assertions.assertNotNull(crewGroup.searchCrew("아마"));
    }

    @DisplayName("크루가 존재하지 않으면 에러를 발생합니다.")
    @Test
    void searchCrewErrorTest() {
        CrewGroup crewGroup = new CrewGroup();
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 2, 13, 0));
        crewGroup.addCrew("가콩", LocalDateTime.of(2024, 12, 3, 15, 0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> crewGroup.searchCrew("이든"));
    }

    @DisplayName("제적 위험자를 계산합니다")
    @Test
    void calculateAttendanceAlertLevelTest() {
        CrewGroup crewGroup = new CrewGroup();
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 2, 14, 0));
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 3, 10, 45));
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 4, 10, 31));

        List<Crew> alertCrews = crewGroup.getAllAttendanceAlertLevel();
        Assertions.assertTrue(alertCrews.contains(new Crew("아마")));
    }
}