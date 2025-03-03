package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MonthTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 13})
    void 출석_날짜는_1월부터_12월까지만_지원한다(int month) {
        // when & then
        assertThatThrownBy(() -> new Month(month))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("월은 1 이상 12 이하여야 합니다.");
    }
}
