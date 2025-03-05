package attendance.utils;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void 숫자_검증() {
        //given
        String inputDate = "10";
        LocalDate localDate = LocalDate.of(2025, 2, 24);

        //when & then
        assertDoesNotThrow(() -> Parser.toDateTime(inputDate, localDate));
    }

    @Test
    void 숫자_예외() {
        //given
        String inputDate = "십";
        LocalDate localDate = LocalDate.of(2025, 2, 24);
        Assertions.assertThatThrownBy(() -> Parser.toDateTime(inputDate, localDate))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.DATE_NUMBER_FORMAT.getMessage());
    }

    @Test
    void 범위_예외() {
        //given
        String inputDate = "29";
        LocalDate localDate = LocalDate.of(2025, 2, 24);

        Assertions.assertThatThrownBy(() -> Parser.toDateTime(inputDate, localDate))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.INVALID_DATE_RANGE.getMessage());
    }

    @Test
    void 이전_타임_대체() {
        //given
        String inputTime = "12:59";
        LocalDate localDate = LocalDate.of(2025, 2, 24);

        //when
        LocalDateTime parsedLocalDateTime = Parser.toTime(inputTime, localDate);

        //then
        Assertions.assertThat(parsedLocalDateTime.getHour()).isEqualTo(12);
        Assertions.assertThat(parsedLocalDateTime.getMinute()).isEqualTo(59);
    }

    @Test
    void 시간_숫자_예외() {
        //given
        String inputTime = "우:가";
        LocalDate localDate = LocalDate.of(2025, 2, 24);

        //when & then
        Assertions.assertThatThrownBy(() -> Parser.toTime(inputTime, localDate))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.TIME_FORMAT_ERROR.getMessage());
    }

    @Test
    void 시간_숫자_포멧_예외() {
        //given
        String inputTime = "25:00";
        LocalDate localDate = LocalDate.of(2025, 2, 24);

        //when & then
        Assertions.assertThatThrownBy(() -> Parser.toTime(inputTime, localDate))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.TIME_FORMAT_ERROR.getMessage());
    }
}