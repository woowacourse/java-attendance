package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewTest {

    @ParameterizedTest
    @CsvSource({"쿠키, 빙봉, false", "쿠키, 쿠키, true"})
    void 크루의_이름이_같은지_알_수_있다(String name1, String name2, boolean expected) {
        //given
        Crew crew1 = Crew.of(name1);
        //when
        boolean actual = crew1.isEqualName(name2);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"쿠키, 빙봉, 1", "빙봉, 쿠키, -1", "쿠키, 쿠키, 0"})
    void 크루의_이름으로_비교할_수_있다(String name1, String name2, int expected) {
        //given
        Crew crew1 = Crew.of(name1);
        Crew crew2 = Crew.of(name2);
        //when
        int actual = crew1.compareTo(crew2);
        //then
        if (expected > 0) {
            assertThat(actual).isGreaterThan(expected);
        }
        if (expected == 0) {
            assertThat(actual).isEqualTo(expected);
        }
        if (expected < 0) {
            assertThat(actual).isLessThan(expected);
        }
    }
}
