package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("공휴일에 츨석을 시도하면 예외 처리")
    @Test
    void invalidateDateTimeAboutHoliday() {
        // given
        final String inputDate = "2024-12-25 10:12";

        // when
        // then
        assertThatThrownBy(() -> AttendanceDateTime.from(inputDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
