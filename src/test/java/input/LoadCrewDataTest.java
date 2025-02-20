package input;

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
        Assertions.assertThat(crews.findCrew("빙티").getAttendanceHistory().stream())
                .hasSize(10);
    }

}
