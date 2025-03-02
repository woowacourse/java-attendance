package domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class NicknameTest {

    @ParameterizedTest
    @ValueSource(strings = {" ", ""})
    void 닉네임이_공백인_경우_예외를_발생시킨다(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 닉네임은 공백일 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"짱수짱수", "빙봉빙봉빙", "쿠키쿠키쿠"})
    void 닉네임이_4자_이상이면_예외를_발생시킨다(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 닉네임의 길이는 3자 이하여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"짱수짱", "빙봉", "쿠"})
    void 닉네임이_3자_이하이면_예외를_발생시키지_않는다(String nickname) {
        assertDoesNotThrow(() -> new Nickname(nickname));
    }
}
