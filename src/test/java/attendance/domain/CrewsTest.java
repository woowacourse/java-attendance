package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_CREW_ALREADY_EXIST;
import static attendance.error.ErrorMessage.ERROR_CREW_NOT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @DisplayName("크루를 추가하면 해당 크루가 존재해야 한다.")
    @Test
    void testAddCrew() {
        Crews crews = new Crews();
        Crew crew = new Crew("빙티");

        crews.addCrew(crew);

        assertThat(crews.contains("빙티")).isTrue();
    }

    @DisplayName("존재하는 크루 이름으로 findByName 호출 시 올바른 Crew를 반환한다.")
    @Test
    void testFindByNameSuccess() {
        Crews crews = new Crews();
        Crew crew = new Crew("빙티");
        crews.addCrew(crew);

        Crew found = crews.findByName("빙티");

        assertThat(found).isEqualTo(crew);
    }

    @DisplayName("존재하지 않는 크루 이름으로 findByName 호출 시 예외를 발생시킨다.")
    @Test
    void testFindByNameNotFound() {
        Crews crews = new Crews();

        assertThatThrownBy(() -> crews.findByName("없는크루"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_CREW_NOT_EXIST);
    }

    @DisplayName("중복된 크루를 추가하면 예외를 발생시킨다.")
    @Test
    void testAddDuplicateCrew() {
        Crews crews = new Crews();
        Crew crew1 = new Crew("빙티");
        crews.addCrew(crew1);

        Crew crew2 = new Crew("빙티");
        assertThatThrownBy(() -> crews.addCrew(crew2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_CREW_ALREADY_EXIST);
    }

}