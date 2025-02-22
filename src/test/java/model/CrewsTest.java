package model;

import static attendance.error.ErrorMessage.ERROR_CREW_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.Crew;
import attendance.model.Crews;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 등록되지_않은_닉네임으로_찾으면_예외가_발생한다() {
        Crews crews = new Crews();
        assertThatThrownBy(() -> crews.findCrew("빙티"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_CREW_NOT_FOUND);
    }

    @Test
    void 크루의_이름으로_크루가_존재하지_않으면_false를_반환한다() {
        Crews crews = new Crews();
        assertThat(crews.containsCrew("빙티")).isFalse();
    }

    @Test
    void 크루의_이름으로_크루가_존재하면_true를_반환한다() {
        Crews crews = new Crews();
        crews.add(new Crew("빙티"));
        assertThat(crews.containsCrew("빙티")).isTrue();
    }
}
