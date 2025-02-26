package model;

import attendance.model.Panalty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PenaltyTest {

    @Test
    void 지각을_결석으로_환산한_결석_횟수가_2회면_면담이다() {
        // given

        // when
        Panalty panalty = Panalty.of(1, 3);

        // then
        Assertions.assertThat(panalty).isEqualTo(Panalty.WARN);
    }

    @Test
    void 지각을_결석으로_환산한_결석_횟수가_3회_이상_5회_이하면_면담이다() {
        // given

        // when
        Panalty panalty = Panalty.of(4, 3);

        // then
        Assertions.assertThat(panalty).isEqualTo(Panalty.INTERVIEW);
    }

    @Test
    void 지각을_결석으로_환산한_결석_횟수가_5회_초과면_면담이다() {
        // given

        // when
        Panalty panalty = Panalty.of(5, 3);

        // then
        Assertions.assertThat(panalty).isEqualTo(Panalty.DISMISSAL);
    }
}
