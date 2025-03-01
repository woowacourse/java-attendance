package domain;

import static org.assertj.core.api.Assertions.assertThat;

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
}