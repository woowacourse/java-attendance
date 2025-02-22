package attendance.model;

import static attendance.error.ErrorMessage.ERROR_CREW_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 등록되지_않은_닉네임으로_찾으면_예외가_발생한다() {
        //given
        Crews crews = new Crews(new ArrayList<>());

        //when & then
        assertThatThrownBy(() -> crews.findCrew("빙티"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_CREW_NOT_FOUND);
    }

    @Test
    void 크루의_이름으로_크루가_존재하지_않으면_false를_반환한다() {
        //given
        Crews crews = new Crews(new ArrayList<>());

        //when
        boolean containsCrew = crews.containsCrew("빙티");

        //then
        assertThat(containsCrew).isFalse();
    }

    @Test
    void 크루의_이름으로_크루가_존재하면_true를_반환한다() {
        //given
        Crews crews = new Crews(new ArrayList<>());
        crews.add(new Crew("빙티"));

        //when
        boolean containsCrew = crews.containsCrew("빙티");

        //then
        assertThat(containsCrew).isTrue();
    }
}
