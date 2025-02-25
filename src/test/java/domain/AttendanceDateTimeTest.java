package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDateTimeTest {

    @DisplayName("시간 입력 형식이 올바르면 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"2024-12-13 10:12", "2024-01-01 22:33"})
    void validDateTime(final String input) {
        // given
        // when
        // then
        assertThatCode(() -> AttendanceDateTime.from(input))
                .doesNotThrowAnyException();
    }

    @DisplayName("시간 입력 형식이 올바르지 않으면 예외 처리")
    @ParameterizedTest
    @ValueSource(strings = {"2024/12/13 10:12", "2024|01|01 22:33", "2024-01-01 22-33"})
    void invalidDateTime(final String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> AttendanceDateTime.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
