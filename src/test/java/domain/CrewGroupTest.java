package domain;

import java.time.LocalDateTime;
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
}