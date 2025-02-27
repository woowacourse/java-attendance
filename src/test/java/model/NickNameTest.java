package model;

import java.util.regex.Pattern;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NickNameTest {

    @Test
    @DisplayName("한글만 검증하는 정규표현식인 지 성공 테스트")
    void validateLanguageSuccess() {

        // given
        final String nicknameInput = "가나";
        final String expected = "가나";

        // when
        final Nickname nickname = new Nickname(nicknameInput);

        // then
        Assertions.assertThat(nickname.getValue()).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("한글만 검증하는 정규표현식인 지 실패 테스트")
    @ValueSource(strings = {"s가", "가s다", "가s"})
    void validateLanguageFailure(final String nickname) {

        // given
        // when
        // then
        Assertions.assertThatThrownBy(
                () -> new Nickname(nickname)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("올바른 정규 표현식인지 검증 테스트")
    void validRegularExpression() {

        // given
        final String validNickname = "가나";
        final String invalidNickname = "world";
        final String prefix = "^[가-힣]{2,4}$";

        // when
        final boolean validMatchResult = Pattern.matches(prefix, validNickname);
        final boolean invalidMatchResult = Pattern.matches(prefix, invalidNickname);

        // then

        org.junit.jupiter.api.Assertions.assertAll(
                () -> Assertions.assertThat(validMatchResult).isTrue(),
                () -> Assertions.assertThat(invalidMatchResult).isFalse()
        );
    }
}
