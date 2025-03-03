package attendance.domain;

import static attendance.exception.ErrorMessage.INVALID_NICKNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {
    @DisplayName("닉네임이_같으면_동일한_객체_이다")
    @CsvSource(value = {"레오:True", "오레오:False"}, delimiterString = ":")
    @ParameterizedTest
    void equals(String nickname, boolean expected) {
        //given
        Nickname crewNickname = new Nickname("레오");

        //when
        boolean result = crewNickname.equals(new Nickname(nickname));

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("null_또는_공백을_포함하면_예외를_던진다")
    @NullAndEmptySource
    @EmptySource
    @ValueSource(strings = {" ", "레오 ", " 레오", "레 오"})
    @ParameterizedTest
    void should_ThrowException_WhenNicknameIsNullOrEmpty(String nickname) {
        //when
        //then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_NICKNAME.getMessage());
    }
}
