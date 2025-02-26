package repository;

import domain.Crew;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewRepositoryTest {
    @AfterEach
    void clearCrewRepository() {
        CrewRepository.clear();
    }

    @Test
    @DisplayName("존재하는 크루라면 true를 반환한다")
    void crewExistsTest_true() {
        // given
        String nickname = "myname";
        Crew crew = new Crew(nickname);
        CrewRepository.addCrew(crew);

        // when & then
        Assertions.assertThat(CrewRepository.existsCrew(nickname)).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 크루라면 false를 반환한다")
    void crewExistsTest_false() {
        // given
        String nickname = "myname";
        Crew crew = new Crew(nickname);
        CrewRepository.addCrew(crew);

        // when & then
        Assertions.assertThat(CrewRepository.existsCrew("yourname")).isFalse();
    }
}