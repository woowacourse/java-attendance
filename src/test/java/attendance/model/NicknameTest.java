package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("크루 닉네임 테스트")
class NicknameTest {

    @DisplayName("정상적인 닉네임인 경우 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"벨로", "슬링키"})
    void shouldNotThrowException_WhenValidNickname(String validNickname) {
        // when & then
        assertThatCode(() -> new Nickname(validNickname))
                .doesNotThrowAnyException();
    }

    @DisplayName("같은 닉네임(값)을 가진 경우 동일하게 간주한다.")
    @Test
    void shouldEquals_WhenSameNicknameValue() {
        // given
        Nickname nickname = new Nickname("벨로");

        // when & then
        assertThat(nickname.equals(new Nickname("벨로")))
                .isTrue();
    }

    @DisplayName("닉네임 길이가 2~4자가 아닌 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"가", "가나다라마"})
    void shouldThrowException_WhenNicknameLengthIsInvalid(String invalidNickname) {
        // when & then
        assertThatCode(() -> new Nickname(invalidNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 길이는 2자 이상 4자 이하여야 합니다. 입력: %s".formatted(invalidNickname));
    }

    @DisplayName("닉네임이 한글로만 구성되지 않은 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"hi", "nice", "12", "벨로1"})
    void shouldThrowException_WhenNicknameIsNotKorean(String englishNickname) {
        // when & then
        assertThatCode(() -> new Nickname(englishNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 한글만 사용할 수 있습니다. 입력: %s".formatted(englishNickname));
    }

    @DisplayName("닉네임이 null 또는 비어있는 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void shouldThrowException_WhenNicknameIsNullOrEmpty(String invalidNickname) {
        // when & then
        assertThatCode(() -> new Nickname(invalidNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임이 null 또는 비어있습니다.");
    }

    @DisplayName("닉네임이 공백으로만 구성되어 있는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    void shouldThrowException_WhenNicknameContainsSpace(String invalidNickname) {
        // when & then
        assertThatCode(() -> new Nickname(invalidNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임이 공백으로만 구성되어 있습니다.");
    }

    @DisplayName("닉네임은 오름차순으로 정렬할 수 있다.")
    @Test
    void compareToTest() {
        // given
        Nickname neo = new Nickname("네오");
        Nickname bello = new Nickname("벨로");

        // when & then
        assertThat(neo.compareTo(bello))
                .isNegative();
    }
}
