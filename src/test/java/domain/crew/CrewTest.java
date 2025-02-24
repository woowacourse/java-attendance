package domain.crew;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {
    @Test
    @DisplayName("이름이 같으면 같은 크루로 인식한다")
    void testEquals() {
        // given
        String name = "히로";

        Crew crew = Crew.of(name);
        Crew duplicatedCrew = Crew.of(name);

        // when & then
        Assertions.assertThat(crew.equals(duplicatedCrew)).isTrue();
    }
}
