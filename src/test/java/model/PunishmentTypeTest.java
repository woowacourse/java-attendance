package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PunishmentTypeTest {

    @ParameterizedTest
    @CsvSource({
            "1, NONE",
            "2, WARNING",
            "3, MEETING",
            "5, MEETING",
            "6, EXPULSION"
    })
    void 결석_횟수에_따라_제적위험도를_결정한다(int count, PunishmentType expected) {
        //given
        //when
        PunishmentType actual = PunishmentType.calculateType(count);
        //then
        assertThat(actual).isEqualTo(expected);
    }


    @ParameterizedTest
    @CsvSource({
            "NONE, WARNING, 1",
            "NONE, MEETING, 2",
            "WARNING, EXPULSION, 2"
    })
    void 제적위험도의_차이를_계산할_수_있다(PunishmentType type1, PunishmentType type2, int expected) {
        //given
        //when
        int actual = type1.comparePriority(type2);
        //then
        assertThat(actual).isEqualTo(expected);
    }
}
