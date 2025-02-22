package attendance.controller;

import static attendance.error.ErrorMessage.ERROR_NOT_NUMERIC;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void 숫자가_아닌_문자를_숫자로_변환하면_예외가_발생한다() {
        String str = "s";

        assertThatThrownBy(() -> Parser.parseInt(str))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_NOT_NUMERIC);
    }

}