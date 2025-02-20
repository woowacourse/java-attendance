package domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    void isCheckedCrewTest() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 13, 3);
        Crew crew = new Crew("아마");
        crew.addAttendance(dateTime);
        Assertions.assertTrue(crew.isAlreadyChecked(dateTime));
    }

}