package attendance.controller.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TimeFormatterTest {
    private LocalDate now = LocalDate.of(2024, 12, 20);

    @ParameterizedTest
    @ValueSource(strings = {"9:1", "09:1", "9:01"})
    void 입력이_포맷과_다르면_예외(String input) {
        assertThatThrownBy(() -> TimeFormatter.format(now, input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}