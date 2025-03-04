package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewsTest {

    @ParameterizedTest
    @CsvSource({
            "pobi, true",
            "neo, true",
            "surf, false",
    })
    void 크루가_포함되어_있는지_확인할_수_있다(String nickname, boolean expected) {
        //given
        Crews crews = new Crews("pobi", "neo");

        //when
        boolean actual = crews.contains(nickname);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
