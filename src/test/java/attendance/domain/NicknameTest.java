package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
}
