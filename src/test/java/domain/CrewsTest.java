package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 닉네임으로_크루를_조회한다() {
        //given
        Crew crew = new Crew("쿠키");
        Crews crews = new Crews(Set.of(crew));
        //when
        Crew actual = crews.findByNickname("쿠키").get();
        //then
        assertThat(actual).isEqualTo(crew);
    }

    @Test
    void 닉네임을_찾을수_없으면_예외를_발생시킨다() {
        //given
        Crew crew = new Crew("쿠키");
        Crews crews = new Crews(Set.of(crew));
        //when & then
        assertThatThrownBy(() -> crews.findByNickname("쿠기"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 닉네임의 크루가 존재하지 않습니다.");
    }
}