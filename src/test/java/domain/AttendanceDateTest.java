package domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.date.AttendanceDate;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceDateTest {

    private static Stream<Arguments> getTestCasesOfTestGetDayOfWeek() {
        return Stream.of(
                Arguments.of(1, 7),
                Arguments.of(2, 1),
                Arguments.of(7, 6),
                Arguments.of(26, 4),
                Arguments.of(14, 6),
                Arguments.of(1, 7)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestCasesOfTestGetDayOfWeek")
    @DisplayName("한 주의 몇번째인지를 의미하는 숫자를 통해 요일을 반환한다")
    void testGetDayOfWeek(int day, int expected) {
        // given
        AttendanceDate attendanceDate = new AttendanceDate(day);

        // when
        int actual = attendanceDate.getDayOfWeek();

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> getTestCasesForIsRestDay() {
        return Stream.of(
                Arguments.of(1, true),
                Arguments.of(2, false),
                Arguments.of(8, true),
                Arguments.of(25, true)
        );
    }

    @ParameterizedTest
    @MethodSource("getTestCasesForIsRestDay")
    @DisplayName("휴일 여부를 판단한다")
    void testIsRestDay(int day, boolean expected) {
        // when
        boolean actual = AttendanceDate.isRestDay(day);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 32, 100})
    @DisplayName("유효한 날짜가 아닌 경우 예외를 던진다")
    void testValidateThrowsException(int day) {
        // when & then
        assertThatThrownBy(() -> new AttendanceDate(day))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 31, 5})
    @DisplayName("유효한 날짜인 경우 예외를 던지지 않는다")
    void testValidateDoesNotThrowException(int day) {
        // when & then
        assertThatNoException().isThrownBy(() -> new AttendanceDate(day));
    }
}
