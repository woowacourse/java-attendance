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

    @Test
    void 이전_타임_대체() {
        //given
        String inputTime = "12:59";
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 24, 13, 31);

        //when
        LocalDateTime parsedLocalDateTime = Parser.toTime(inputTime, localDateTime);

        //then
        Assertions.assertThat(parsedLocalDateTime.getHour()).isEqualTo(12);
        Assertions.assertThat(parsedLocalDateTime.getMinute()).isEqualTo(59);
    }

    @Test
    void 시간_숫자_예외() {
        //given
        String inputTime = "우:가";
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 24, 13, 31);

        //when & then
        Assertions.assertThatThrownBy(() -> Parser.toTime(inputTime, localDateTime))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.TIME_FORMAT_ERROR.getMessage());
    }

    @Test
    void 시간_숫자_포멧_예외() {
        //given
        String inputTime = "25:00";
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 24, 9, 59);

        //when & then
        Assertions.assertThatThrownBy(() -> Parser.toTime(inputTime, localDateTime))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.TIME_FORMAT_ERROR.getMessage());
    }
}