package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    @DisplayName("크루 닉네임이 같은지 확인한다")
    void isEqualName() {
        // given
        Crew crew = Crew.of("미소");
        String nickname = "미소";

        // when
        boolean result = crew.isEqualName(nickname);

        // then
        Assertions.assertThat(result).isTrue();
    }
}