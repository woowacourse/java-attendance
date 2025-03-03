package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.crew.Nickname;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {

    @DisplayName("올바르지 않은 형식의 닉네임을 입력 받을시 예외 처리")
    @ParameterizedTest
    @ValueSource(strings = {"hi", "@", "2345", "히포23", "도+미", "\\", "   이름"})
    void invalidNickname(final String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Nickname(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("올바른 형식의 닉네임을 입력 받을시 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"히포", "히스타", "히로히로"})
    void validaNickname(final String input) {
        // given
        // when
        // then
        assertThatCode(() -> new Nickname(input))
                .doesNotThrowAnyException();
    }
}
