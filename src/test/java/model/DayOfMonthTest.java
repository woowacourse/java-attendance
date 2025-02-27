package model;

import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DayOfMonthTest {

    @ParameterizedTest
    @DisplayName("String 입력값으로 객체가 잘 생성되는 지 성공 테스트")
    @MethodSource("ofSuccessSources")
    void ofSuccess(final String dayOfMonthInput, final int expected) {
        // given
        // when
        final DayOfMonth dayOfMonth = DayOfMonth.of(dayOfMonthInput);

        // then
        Assertions.assertThat(dayOfMonth.getValue()).isEqualTo(expected);
    }

    private static Stream<Arguments> ofSuccessSources() {
        return Stream.of(
                Arguments.arguments("1", 1),
                Arguments.arguments("7", 7),
                Arguments.arguments("31", 31)
        );
    }
}
