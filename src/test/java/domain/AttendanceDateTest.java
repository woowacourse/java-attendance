package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDateTest {

    @DisplayName("운영 날짜가 아닐 경우 예외 처리")
    @ParameterizedTest
    @ValueSource(strings = {"1", "8", "25"})
    void invalidDate(final String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> AttendanceDate.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
