import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Crew;
import domain.Crews;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    void test1() {
        List<Crew> crewList = new ArrayList<>();
        crewList.add(new Crew("폰트", "2024-12-13 10:08"));
        crewList.add(new Crew("슬링키", "2024-12-09 13:03"));

        Crews crews = new Crews(crewList);

        Crew crew = crews.findCrew("폰트");

        assertThat(crew.getName()).isEqualTo("폰트");
    }

    @Test
    void test2() {
        List<Crew> crewList = new ArrayList<>();
        crewList.add(new Crew("폰트", "2024-12-13 10:08"));
        crewList.add(new Crew("슬링키", "2024-12-09 13:03"));

        Crews crews = new Crews(crewList);

        Crew crew = crews.findCrew("슬링키");

        assertThat(crew.getName()).isEqualTo("슬링키");
    }

    @Test
    void test3() {
        List<Crew> crewList = new ArrayList<>();
        crewList.add(new Crew("폰트", "2024-12-13 10:08"));
        crewList.add(new Crew("슬링키", "2024-12-09 13:03"));

        Crews crews = new Crews(crewList);

        crews.ifFindNameAddTime("슬링키", "2024-12-09 13:03");

        assertThat(crews.findCrew("슬링키").getAttendTimes().size())
                .isEqualTo(2);
    }

    @Test
    void test4() {
        List<Crew> crewList = new ArrayList<>();
        crewList.add(new Crew("폰트", "2024-12-13 10:08"));
        crewList.add(new Crew("슬링키", "2024-12-09 13:03"));

        Crews crews = new Crews(crewList);

        crews.ifFindNameAddTime("포비", "2024-12-09 13:03");

        assertThat(crews.getCrews().size())
                .isEqualTo(3);
    }

}
