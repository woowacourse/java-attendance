package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTimeTest {

    @ParameterizedTest
    @DisplayName("String 자료형의 입력값이 LocalTime 자료형으로 잘 파싱되는 지 성공 테스트")
    @ValueSource(strings = {"09:05", "9:05", "09:5", "9:5"})
    void ofSuccess(final String timeInput) {
        // given
        // when
        // then
        Assertions.assertThatCode(
                () -> AttendanceTime.of(timeInput)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("유효하지 않는 형식의 입력값이 파싱에 실패하는 지 테스트")
    @ValueSource(strings = {"0905", "9::05", "95:5"})
    void ofFailureByInvalidInputFormat(final String timeInput) {
        // given
        // when
        // then
        Assertions.assertThatThrownBy(
                () -> AttendanceTime.of(timeInput)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
