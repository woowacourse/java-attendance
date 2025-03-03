package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewTest {

    @ParameterizedTest
    @CsvSource({
            "쿠키, true", "쿠기, false"
    })
    void 닉네임이_같은지_비교한다(String nickname, boolean expected) {
        //given
        Crew crew = new Crew("쿠키");
        //when
        boolean actual = crew.isSameNickname(nickname);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
