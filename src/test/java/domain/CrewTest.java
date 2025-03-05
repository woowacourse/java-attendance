package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @Test
    void 크루_객체를_동일한_이름으로_비교할_수_있다() {
        // given
        Crew crew1 = new Crew("fora");
        Crew crew2 = new Crew("fora");

        // when & then
        Assertions.assertThat(crew1).isEqualTo(crew2);
    }
}
