package attendance.domain.crew;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @DisplayName("닉네임이 동일한지 체크할 수 있다")
    @Test
    void 닉네임이_동일한지_체크할_수_있다() {
        String expectedName = "쿠키";
        Crew crew = new Crew(expectedName);

        assertThat(crew.isSameNickname(expectedName)).isTrue();
    }
}