package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.Crew;
import attendance.model.Crews;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    void test1() {
        Crews crews = new Crews();
        Assertions.assertThat(crews).isNotNull();
    }

    @Test
    void test2() {
        Crews crews = new Crews();
        Crew crew = new Crew("빙티");
        crews.add(crew);
        Assertions.assertThat(crews.getCrews()).hasSize(1);
    }

    @DisplayName("등록되지 않은 닉네임을 찾으려고 할 때 예외가 발생한다")
    @Test
    void findCrew() {
        // given
        Crews crews = new Crews();

        assertThatThrownBy(() -> crews.findCrew(new Crew("빙티")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
