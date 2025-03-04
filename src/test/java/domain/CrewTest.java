package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("크루에 대한 테스트")
class CrewTest {

    @DisplayName("크루가 해당 닉네임을 가졌는 지 확인할 수 있다.")
    @Test
    void ableToCheckCrewHasName() {
        Crew crew = new Crew("크루");
        boolean result = crew.hasName("크루");
        assertThat(result).isTrue();
    }
}
