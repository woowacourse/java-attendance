package attendance.domain;

import static attendance.error.ErrorMessage.ERROR_NAME_LENGTH;
import static attendance.error.ErrorMessage.ERROR_NAME_NULL_OR_BLANK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CrewTest {

    @DisplayName("유효한 이름을 사용하면 크루가 정상적으로 생성된다")
    @ParameterizedTest
    @ValueSource(strings = {"AB", "ABC", "ABCD"})
    void testValidName(String name) {
        Crew crew = new Crew(name);
        assertThat(crew).isNotNull();
    }

    @DisplayName("닉네임 길이가 2글자 미만이거나 4글자를 초과하면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"A", "ABCDE"})
    void testNameLengthError(String name) {
        assertThatThrownBy(() -> new Crew(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_NAME_LENGTH);
    }

    @DisplayName("닉네임이 null이거나 비어있으면 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "   "})
    void testNameNullOrBlank(String name) {
        assertThatThrownBy(() -> new Crew(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_NAME_NULL_OR_BLANK);
    }

}