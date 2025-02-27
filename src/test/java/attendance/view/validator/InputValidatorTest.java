package attendance.view.validator;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import attendance.exception.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class InputValidatorTest {

    @DisplayName("비어있는 값이 입력되었는지 검증할 수 있다")
    @ParameterizedTest
    @NullAndEmptySource
    void 비어있는_값이_입력되었는지_검증할_수_있다(String input) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> InputValidator.validateBlank(input))
                .withMessage(ExceptionMessage.BLANK_INPUT.getMessage());
    }

    @DisplayName("숫자 형식 아닌 데이터가 입력되었는지 검증할 수 있다")
    @Test
    void 숫자_형식_아닌_데이터가_입력되었는지_검증할_수_있다() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> InputValidator.validateNonNumeric("숫자아님"))
                .withMessage(ExceptionMessage.NOT_NUMERIC_INPUT.getMessage());
    }

}