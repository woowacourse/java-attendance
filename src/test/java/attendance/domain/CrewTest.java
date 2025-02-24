package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CrewTest {
    @DisplayName("크루_생성_시_이름이_null_또는_공백이면_예외를_던진다")
    @NullSource
    @EmptySource
    @ValueSource(strings = {" ", "  "})
    @ParameterizedTest
    void should_ThrowException_WhenCrewNameIsNullOrBlank(String name) {
        //then
        assertThatThrownBy(() -> new Crew(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루 이름은 공백일 수 없습니다.");
    }
}

