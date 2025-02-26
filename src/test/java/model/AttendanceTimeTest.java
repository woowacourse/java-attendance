package model;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceTimeTest {

    @ParameterizedTest
    @DisplayName("String 자료형의 입력값이 LocalTime 자료형으로 잘 파싱되는 지 성공 테스트")
    @ValueSource(strings = {"09:05", "9:05", "09:5", "9:5"})
    void ofSuccessByStringInput(final String timeInput) {
        // given
        // when
        // then
        Assertions.assertThatCode(
                () -> AttendanceTime.of(timeInput)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("유효하지 않는 String형식의 입력값이 파싱에 실패하는 지 테스트")
    @ValueSource(strings = {"0905", "9::05", "95:5"})
    void ofFailureByInvalidStringInputFormat(final String timeInput) {
        // given
        // when
        // then
        Assertions.assertThatThrownBy(
                () -> AttendanceTime.of(timeInput)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("int hour, int minute 입력값이 파싱에 성공하는 지 테스트")
    @MethodSource("ofSuccessIntegerSources")
    void ofSuccessByIntegerInputs(final int hour, final int minute) {
        // given
        // when
        // then
        Assertions.assertThatCode(
                () -> AttendanceTime.of(hour, minute)
        ).doesNotThrowAnyException();
    }

    private static Stream<Arguments> ofSuccessIntegerSources() {
        return Stream.of(
                Arguments.arguments(2,59),
                Arguments.arguments(23, 0)
        );
    }

    @ParameterizedTest
    @DisplayName("유효하지 않는 범위값의 int hour, int minute 입력값이 파싱에 실패하는 지 테스트")
    @MethodSource("ofFailureIntegerSources")
    void ofFailureByInvalidIntegerInputs(final int hour, final int minute) {
        // given
        // when
        // then
        Assertions.assertThatThrownBy(
                () -> AttendanceTime.of(hour, minute)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> ofFailureIntegerSources() {
        return Stream.of(
                Arguments.arguments(25,59),
                Arguments.arguments(11, 61)
        );
    }
}
