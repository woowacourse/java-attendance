package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewTest {


    @Test
    void 크루_이름으로_크루를_생성한다() {
        // when
        Crew crew = Crew.fromName("제프리");

        // then
        Assertions.assertThat(crew.getName()).isEqualTo("제프리");
    }

}
