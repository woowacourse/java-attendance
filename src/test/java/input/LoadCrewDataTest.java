package input;

import attendance.model.Crew;
import attendance.model.CrewDataLoader;
import attendance.model.Crews;
import attendance.model.CustomLocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoadCrewDataTest {

    //@DisplayName("")
    @Test
    void test_read2() {
        Crews crews = new Crews();
        CrewDataLoader crewDataLoader = new CrewDataLoader(crews, CustomLocalDateTime.now());
        crewDataLoader.load("attendances.csv");
        Assertions.assertThat(crews.findCrew(new Crew("빙티")).get().getAttendanceHistory().getAttendanceHistory())
                .hasSize(10);
    }

}
