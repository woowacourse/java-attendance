package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {

    @Test
    void 올바른_닉네임을_입력한다면_통과() {
        // given
        String input = "칼리";

        // when
        final Nickname nickname = new Nickname(input);

        // then
        assertThat(nickname.getNickname()).isEqualTo(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"add", "바", "11", "바add", "가나다라마"})
    void 올바르지_않은_형식의_닉네임을_입력시_예외(String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> new Nickname(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
