package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

public class CrewTest {

    @Test
    void 닉네임을_가지고_크루를_생성한다() {
        // Given
        String nickname = "빙봉";

        // When
        Crew crew = new Crew(nickname);

        // Then
        assertThat(crew).isEqualTo(new Crew("빙봉"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"가", "가나다라마"})
    void 닉네임_길이_범위를_벗어나면_크루가_생성되지_않는다(String nickname) {
        // When & Then
        assertThatThrownBy(() -> new Crew(nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
