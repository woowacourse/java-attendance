package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NicknameTest {

    @Test
    @DisplayName("크루 닉네임은 2글자 이상이 아니라면 예외가 발생한다.")
    void nicknameLessThan2charThrowException() {
        // given
        String validNickname = "강산";
        String invalidNickname = "산";

        // when
        // then
        assertThatCode(() -> Nickname.from(validNickname))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> Nickname.from(invalidNickname))
                .hasMessage("닉네임은 2글자 이상 입력해야 합니다.");
    }
}
