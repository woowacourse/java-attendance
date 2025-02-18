package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewsTest {
    @Test
    void searchCrew() {
        Crew actualCrew = new Crew("아마");
        List<Crew> testCrews = new ArrayList<>(List.of(actualCrew));
        Crews crews = new Crews(testCrews);
        Assertions.assertSame(crews.searchCrew("아마"), actualCrew);
    }
}