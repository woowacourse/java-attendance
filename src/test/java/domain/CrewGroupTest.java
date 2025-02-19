package domain;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewGroupTest {
    @Test
    void searchCrew() {
        CrewGroup crewGroup = new CrewGroup();
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 2, 13, 0));
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 3, 15, 0));

        Assertions.assertNotNull(crewGroup.searchCrew("아마"));
    }

    @Test
    void calculateAttendanceAlertLevelTest() {
        CrewGroup crewGroup = new CrewGroup();
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 2, 14, 0));
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 3, 10, 45));
        crewGroup.addCrew("아마", LocalDateTime.of(2024, 12, 4, 10, 31));

        crewGroup.calculateAllAttendanceCount();

        List<Crew> alertCrews = crewGroup.getAllAttendanceAlertLevel();
        Assertions.assertTrue(alertCrews.contains(new Crew("아마")));
    }
}