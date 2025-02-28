package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TimeMachineTest {

    @Test
    @DisplayName("타임머신으로 2024년 12월의 특정 날짜로 이동할 수 있다")
    void timeTravelAt() {
        // given
        LocalDate timeBeforeTimeTravel = TimeMachine.dateOfNow();

        // when
        TimeMachine.timeTravelAt(29);

        // then
        assertThat(TimeMachine.dateOfNow())
                .isEqualTo(LocalDate.of(2024, 12, 29))
                .isNotEqualTo(timeBeforeTimeTravel);
    }

}