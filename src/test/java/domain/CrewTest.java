package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewTest {

    @ParameterizedTest
    @CsvSource({
            "에드, 에드, true",
            "에드, 제프, false"
    })
    void 같은_nickname_멤버를_가지면_같은_객체로_취급한다(String nickname1, String nickname2, boolean expected) {
        Crew crew1 = new Crew(nickname1);
        Crew crew2 = new Crew(nickname2);

        assertThat(crew1.equals(crew2)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "에드, 에드, true",
            "에드, 제프, false"
    })
    void 같은_nickname_멤버를_가지면_같은_해쉬코드를_반환한다(String nickname1, String nickname2, boolean expected) {
        Crew crew1 = new Crew(nickname1);
        Crew crew2 = new Crew(nickname2);

        assertThat(crew1.hashCode() == crew2.hashCode()).isEqualTo(expected);
    }
}
