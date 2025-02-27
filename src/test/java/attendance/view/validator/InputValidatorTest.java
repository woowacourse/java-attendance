package attendance.view.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.exception.ExceptionMessage;
import attendance.exception.InputException;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class InputValidatorTest {

    private static final int YEAR = 2025;
    private static final Month MONTH = Month.FEBRUARY;
    private static final int LAST_DAY_IN_MONTH = LocalDate.of(YEAR, MONTH, 1).lengthOfMonth();


    @DisplayName("비어있는 값이 입력되었는지 검증할 수 있다")
    @ParameterizedTest
    @NullAndEmptySource
    void 비어있는_값이_입력되었는지_검증할_수_있다(String input) {
        assertThatThrownBy(() -> InputValidator.validateBlank(input))
                .isInstanceOf(InputException.class)
                .hasMessage(ExceptionMessage.BLANK_INPUT.getMessage());
    }

    @DisplayName("숫자 형식 아닌 데이터가 입력되었는지 검증할 수 있다")
    @Test
    void 숫자_형식_아닌_데이터가_입력되었는지_검증할_수_있다() {
        assertThatThrownBy(() -> InputValidator.validateNonNumeric("숫자아님"))
                .isInstanceOf(InputException.class)
                .hasMessage(ExceptionMessage.NOT_NUMERIC_INPUT.getMessage());
    }

    @DisplayName("달에 포한된 날짜가 입력되었는지 검증할 수 있다")
    @ParameterizedTest
    @MethodSource
    void 달에_포한된_날짜가_입력되었는지_검증할_수_있다(int day, boolean isInMonth) {
        if (isInMonth) {
            assertThatCode(() -> InputValidator.validateIsInMonth(YEAR, MONTH, day))
                    .doesNotThrowAnyException();
            return;
        }

        assertThatThrownBy(() -> InputValidator.validateIsInMonth(YEAR, MONTH, day))
                .isInstanceOf(InputException.class)
                .hasMessage(ExceptionMessage.INVALID_DAY_INPUT.getMessage());
    }

    static Stream<Arguments> 달에_포한된_날짜가_입력되었는지_검증할_수_있다() {
        return Stream.of(
                Arguments.of(0, false),
                Arguments.of(1, true),
                Arguments.of(LAST_DAY_IN_MONTH, true),
                Arguments.of(LAST_DAY_IN_MONTH + 1, false)
        );
    }
}