package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewRepositoryTest {
    @BeforeEach
    void initCrewRepository() {
        CrewRepository.clear();
    }

    @Test
    @DisplayName("기존에 존재하는 크루명으로 add하는 경우 예외를 발생시킨다.")
    void addTest() {
        // given
        CrewRepository.addCrew(new Crew("cube"));

        // when & then
        Assertions.assertThatThrownBy(() -> {
            CrewRepository.addCrew(new Crew("cube"));
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
