import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void 크루를_생성_할_수_있다() {
        //given
        String name = "도기";

        //when
        Crew crew = new Crew(name);

        //then
        assertThat(crew.getName()).isEqualTo("도기");
    }
}
