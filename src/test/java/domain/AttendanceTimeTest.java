package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTimeTest {

    @DisplayName("올바른 시간 형식을 입력 받으면 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"01:01", "12:12"})
    void validTimeFormat(final String input) {
        // given
        // when
        // then
        Assertions.assertThatCode(() -> AttendanceTime.from(input))
                .doesNotThrowAnyException();
    }

    @DisplayName("올바르지 않은 시간 형식을 입력 받으면 예외 처리")
    @ParameterizedTest
    @ValueSource(strings = {"01/01", "2:12", "2:2", "01-22", "01:도"})
    void invalidTimeFormat(final String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> AttendanceTime.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("운영 시간이 아니면 예외 처리")
    @ParameterizedTest
    @ValueSource(strings = {"01:00", "07:59", "23:01"})
    void invalidTime(final String input) {
        // given
        // when
        // then
        assertThatThrownBy(() -> AttendanceTime.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("운영 시간이면 객체 생성")
    @ParameterizedTest
    @ValueSource(strings = {"08:00", "10:01", "23:00"})
    void validTime(final String input) {
        // given
        // when
        // then
        Assertions.assertThatCode(() -> AttendanceTime.from(input))
                .doesNotThrowAnyException();
    }
}
