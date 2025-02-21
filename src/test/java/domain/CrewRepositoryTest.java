package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.CrewRepository;

public class CrewRepositoryTest {
    @BeforeEach
    void clearCrewRepository() {
        CrewRepository.clear();
    }
    
    @BeforeEach
    void initCrewRepository() {
        CrewRepository.clear();
    }

    @Test
    @DisplayName("기존에 존재하는 크루명으로 add하는 경우 예외를 발생시킨다.")
    void addCrewTest() {
        // given
        CrewRepository.addCrew(new Crew("cube"));

        // when & then
        assertThatThrownBy(() -> {
            CrewRepository.addCrew(new Crew("cube"));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("해당 닉네임의 크루가 존재하면 true를 반환한다")
    void existsTest_true() {
        // given
        CrewRepository.addCrew(new Crew("cube"));

        // when & then
        assertThat(CrewRepository.exists("cube")).isTrue();
    }

    @Test
    @DisplayName("해당 닉네임의 크루가 존재하지 않으면 false를 반환한다")
    void exists_test_false() {
        // given
        CrewRepository.addCrew(new Crew("cube"));

        // when & then
        assertThat(CrewRepository.exists("babe")).isFalse();
    }

    @Test
    @DisplayName("해당 닉네임의 크루가 존재하지 않으면 예외를 발생시킨다")
    void findByNicknameExceptionTest() {
        // given
        CrewRepository.addCrew(new Crew("cube"));

        // when & then
        assertThatThrownBy(() -> {
            CrewRepository.findByNickname("babe");
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
