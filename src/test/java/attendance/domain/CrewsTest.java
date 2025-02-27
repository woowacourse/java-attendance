package attendance.domain;

import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class CrewsTest {

    @DisplayName("크루 추가")
    @Test
    public void 크루_추가() {
        //given
        String crewName = "우가";

        //when
        Crews crews = new Crews();
        crews.addCrew(crewName);

        //then
        Assertions.assertThat(crews.getCrews().size()).isEqualTo(1);
    }
}
