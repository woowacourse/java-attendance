package view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class FeatureOptionTest {
    private static Stream<Arguments> testCasesForGetFunction() {
        return Stream.of(
                Arguments.of("Q", FeatureOption.QUIT),
                Arguments.of("1", FeatureOption.APPLY_ATTENDANCE),
                Arguments.of("2", FeatureOption.EDIT_ATTENDANCE),
                Arguments.of("3", FeatureOption.CHECK_ATTENDANCE_OF_CREW),
                Arguments.of("4", FeatureOption.CHECK_WARNING_CREW)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForGetFunction")
    @DisplayName("조건에 따라 알맞은 기능 옵션을 가져온다")
    void testGetFunction(String option, FeatureOption expected) {
        // when & then
        assertThat(FeatureOption.getFunction(option)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"FFFF", "-1", "333", "**"})
    @DisplayName("잘못된 기능 옵션에 대한 입력값인 경우 예외를 던진다")
    void testGetFunction(String input) {
        // when & then
        assertThatThrownBy(() -> FeatureOption.getFunction(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
