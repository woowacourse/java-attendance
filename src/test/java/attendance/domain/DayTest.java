package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DayTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 32})
    void 출석_날짜는_1일부터_31일까지만_지원한다(int day) {
        // when & then
        assertThatThrownBy(() -> new Day(day))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("일은 1 이상 31 이하여야 합니다.");
    }
}
