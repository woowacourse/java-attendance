package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CrewTest {

    @Nested
    class InvalidCases {

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", " ", "  "})
        void 크루는_닉네임을_가지고_있어야_한다(String nickname) {
            // when & then
            assertThatThrownBy(() -> new Crew(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루는 닉네임을 가지고 있어야 합니다.");
        }
    }
}
