package domain.crew;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewTest {
    @DisplayName("Crew를 닉네임으로 생성할 수 있다.")
    @Test
    void crewTest1() {
        Crew crew = Crew.from("히스타");
        Assertions.assertThat(crew).isInstanceOf(Crew.class);
    }

    @DisplayName("hasSame() 메서드가 올바르게 동작하는지 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"히스타", "히로"})
    void crewTest2(String nickname) {
        Crew crew = Crew.from(nickname);
        Assertions.assertThat(crew.hasSame(nickname)).isTrue();
    }
}
