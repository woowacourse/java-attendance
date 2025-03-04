package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class NicknameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("닉네임 객체를 생성할 때, 빈 입력이면 예외를 던진다.")
    void throwExceptionWhenEmptyNickName(String nickname) {
        //when & then
        Assertions.assertThatThrownBy(() -> new Nickname(nickname));
    }
}
