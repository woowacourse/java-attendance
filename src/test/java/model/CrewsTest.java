package model;

import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 닉네임으로_크루를_찾을_수_있다() {
        //given
        Crew crew1 = Crew.of("쿠키");
        Crew crew2 = Crew.of("빙봉");
        Crews crews = Crews.of(Set.of(crew1, crew2));
        //when
        Crew foundCrew = crews.findByNickname("쿠키").get();
        //then
        Assertions.assertThat(foundCrew).isEqualTo(crew1);
    }

}