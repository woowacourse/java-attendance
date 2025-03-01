package attendance;

import attendance.domain.Crew;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("출석 등록시 이름을 입력하면 Crew 객체가 생성된다")
    void createCrewTest() {
        //given
        String name = "Lemon";

        //when
        Crew crew = new Crew(name);

        //then
        Assertions.assertThat(crew.getName()).isEqualTo("Lemon");
    }

}
