package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CrewManagerTest {

    @Test
    void add() {
        CrewManager crewManager = new CrewManager();
        Crew crew1 = new Crew("젠슨");
        Crew crew2 = new Crew("레오");

        crewManager.addCrew(crew1);
        crewManager.addCrew(crew2);

        assertThat(crewManager.contains(crew1)).isTrue();
        assertThat(crewManager.contains(crew2)).isTrue();
    }

    @Test
    void findCrew() {
        CrewManager crewManager = new CrewManager();
        Crew crew = new Crew("젠슨");
        crewManager.addCrew(crew);

        Crew findCrew = crewManager.findByCrewName("젠슨");
        assertThat(findCrew).isEqualTo(crew);
    }

    @Test
    void findCrew1() {
        assertThatThrownBy(() -> new CrewManager().findByCrewName("젠슨"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이름을 가진 크루는 없음");
    }
}
