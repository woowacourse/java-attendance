package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDateTimeTest {

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-12 11", "바보", "2024-12-32 11:12", "2024-12-11 99:12"})
    void 잘못된_포멧으로_입력했을_때_예외_처리(String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> AttendanceDateTime.of(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

}