package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewManagerTest {
    @DisplayName("크루를_저장할_수_있다")
    @Test
    void addCrew() {
        //given
        CrewManager crewManager = new CrewManager();
        String crewName = "젠슨";
        Crew crew = new Crew(crewName);

        //when
        boolean result = crewManager.addCrew(crew);

        //then
        assertThat(result).isTrue();
        assertThat(crewManager.findByCrewName(crewName)).isEqualTo(crew);
    }

    @DisplayName("크루의_이름으로_크루를_찾아올_수_있다")
    @Test
    void findByCrewName() {
        //given
        CrewManager crewManager = new CrewManager();
        Crew crew = new Crew("젠슨");
        crewManager.addCrew(crew);

        //when
        Crew result = crewManager.findByCrewName(crew.getName());

        //then
        assertThat(result).isEqualTo(crew);
    }

    @DisplayName("존재하지_않는_크루_이름이면_예외를_던진다")
    @Test
    void should_ThrowException_WhenNotExistsCrewName() {
        CrewManager crewManager = new CrewManager();
        assertThatThrownBy(() -> crewManager.findByCrewName("젠슨"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 이름을 가진 크루는 없음");
    }
}
