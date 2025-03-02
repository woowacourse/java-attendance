package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PenaltyTest {

    @Test
    @DisplayName("결석 횟수 기준으로 패널티를 찾는다.")
    void test1() {
        //given
        final int count = 5;

        //when
        final Penalty penalty = Penalty.findByAbsenceCount(count);

        //then
        assertThat(penalty).isEqualTo(Penalty.INTERVIEW);

    }
}
