package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CrewTest {

    @ValueSource(strings = {"빙", "빙봉빙봉빙"})
    @ParameterizedTest
    void 크루_닉네임_길이_범위를_벗어난_경우_크루_생성이_불가능하다(String wrongRangeName) {
        assertThatThrownBy(() -> new Crew(wrongRangeName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임을_알려주면_크루를_생성한다() {
        Crew crew = new Crew("빙봉");

        assertThat(crew).isEqualTo(new Crew("빙봉"));
    }

    @CsvSource(value = {"빙봉,true", "이든,false"})
    @ParameterizedTest
    void 닉네임을_알려주면_크루의_닉네임과_같은지_알려준다(String nickname, boolean expected) {
        Crew crew = new Crew("빙봉");

        assertThat(crew.isSameNickname(nickname)).isEqualTo(expected);
    }

}
