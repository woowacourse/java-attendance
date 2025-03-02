package attendance.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void 숫자_검증() {
        //given
        String inputDate = "10";
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);

        //when & then
        assertDoesNotThrow(() -> Parser.toDateTime(inputDate, localDateTime));
    }

    @Test
    void 숫자_예외() {
        //given
        String inputDate = "십";
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);

        Assertions.assertThatThrownBy(() -> Parser.toDateTime(inputDate, localDateTime))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.DATE_NUMBER_FORMAT.getMessage());
    }

    @Test
    void 범위_예외() {
        //given
        String inputDate = "29";
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);

        Assertions.assertThatThrownBy(() -> Parser.toDateTime(inputDate, localDateTime))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.INVALID_DATE_RANGE.getMessage());
    }
}