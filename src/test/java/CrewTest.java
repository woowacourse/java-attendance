import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import crew.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {
    @Test
    @DisplayName("크루의 이름으로 동일 여부를 판단한다")
    void test1() {
        // given
        Crew crew = new Crew("히로");
        Crew comparedCrew = new Crew("히로");

        // when & then
        assertThat(crew.equals(comparedCrew)).isTrue();
    }
}
