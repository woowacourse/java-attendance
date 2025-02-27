package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CrewTest {

    @Test
    public void 크루_생성() {
        //given
        String name = "우가";

        //when
        Crew crew = new Crew(name);

        //then
        Assertions.assertThat(crew.getName()).isEqualTo(name);
    }
}
