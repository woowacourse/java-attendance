package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CREW_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @DisplayName("존재하는 크루 이름으로 findByName 호출 시 올바른 Crew를 반환한다.")
    @Test
    void testFindByNameSuccess() {
        String crewName = "빙티";
        Crew crew = new Crew(crewName);
        Crews crews = new Crews(Map.of("빙티", new Crew("빙티")));

        Crew found = crews.findByName("빙티");

        assertThat(found).isEqualTo(crew);
    }

    @DisplayName("존재하지 않는 크루 이름으로 findByName 호출 시 예외를 발생시킨다.")
    @Test
    void testFindByNameNotFound() {
        Crews crews = new Crews(Map.of());

        assertThatThrownBy(() -> crews.findByName("없는크루"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_CREW_NOT_EXIST);
    }

}