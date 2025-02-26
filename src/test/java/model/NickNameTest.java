package model;

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
        Nickname nickname = new Nickname(nicknameInput);

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
}
