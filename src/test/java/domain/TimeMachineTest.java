package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

class TimeMachineTest {

    @ParameterizedTest
    @ValueSource(ints = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28
    })
    void 오늘_날짜를_임의로_결정한다(int dayOfMonth) {
        TimeMachine.timeTravelAt(dayOfMonth);

        Assertions.assertThat(TimeMachine.dateOfNow()).isEqualTo(LocalDate.of(2025, 2, dayOfMonth));
    }

}
