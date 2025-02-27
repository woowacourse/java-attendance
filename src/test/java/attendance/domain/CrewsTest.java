package attendance.domain;

import static attendance.error.ErrorMessage.INVALID_CREW_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void add() {
        Crews crews = Crews.create();
        Crew crew1 = Crew.from("젠슨");
        Crew crew2 = Crew.from("레오");

        crews.addCrew(crew1);
        crews.addCrew(crew2);

        assertThat(crews.contains(crew1)).isTrue();
        assertThat(crews.contains(crew2)).isTrue();
    }

    @Test
    void findCrew() {
        Crews crews = Crews.create();
        Crew crew = Crew.from("젠슨");
        crews.addCrew(crew);

        Crew findCrew = crews.findByCrewName("젠슨");
        assertThat(findCrew).isEqualTo(crew);
    }

    @Test
    void findCrew1() {
        Crews crews = Crews.create();
        assertThatThrownBy(() -> crews.findByCrewName("젠슨"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_CREW_NAME.getMessage());
    }
}
