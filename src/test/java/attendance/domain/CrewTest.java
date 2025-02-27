package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("크루 테스트")
public class CrewTest {

    @Test
    void 닉네임이_동일하면_같은_크루다() {
        Crew duei = new Crew(new Nickname("듀이"));
        Crew anotherDuei = new Crew(new Nickname("듀이"));

        assertThat(duei).isEqualTo(anotherDuei);
    }
}
