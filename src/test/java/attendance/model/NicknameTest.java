package attendance.model;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("닉네임 테스트")
class NicknameTest {

    @DisplayName("정상적인 넥네임인 경우 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"네오", "워니", "브라운"})
    void doesNotThrowException_WhenValidNickname(String validNickname) {
        assertThatCode(() -> new Nickname(validNickname))
                .doesNotThrowAnyException();
    }

    @DisplayName("닉네임이 null 또는 비어있는 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_WhenNicknameIsNullAndEmpty(String invalidNickname) {
        assertThatCode(() -> new Nickname(invalidNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임이 비어있습니다.");
    }

    @DisplayName("닉네임이 2~4자가 아닌 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "brown"})
    void shouldThrowException_WhenInValidLengthNickname(String invalidNickname) {
        assertThatCode(() -> new Nickname(invalidNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 2~4 사이의 글자수여야 합니다.");
    }
}
