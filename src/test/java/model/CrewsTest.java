package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 닉네임으로_크루를_찾을_수_있다() {
        //given
        Crew crew1 = Crew.of("쿠키");
        Crew crew2 = Crew.of("빙봉");
        Crews crews = Crews.of(Set.of(crew1, crew2));
        //when
        Crew foundCrew = crews.findByNickname("쿠키");
        //then
        assertThat(foundCrew).isEqualTo(crew1);
    }

    @Test
    void 등록되지_않은_닉네임인_경우_예외를_발생시킨다() {
        //given
        Crew crew1 = Crew.of("쿠키");
        Crew crew2 = Crew.of("빙봉");
        Crews crews = Crews.of(Set.of(crew1, crew2));
        //when & then
        assertThatThrownBy(() -> crews.findByNickname("빙빙"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 닉네임입니다.");
    }
}
