package util.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static util.parser.DateTimeParser.parseStringToDate;
import static util.parser.DateTimeParser.parseStringToDateTime;
import static util.parser.DateTimeParser.parseStringToTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTimeParserTest {

    @Test
    @DisplayName("문자열을 DateTime으로 변환할 수 있다.")
    void successParseStringToDateTime() {
        LocalDateTime actual = parseStringToDateTime("2024-12-04 10:08");
        LocalDateTime expected = LocalDateTime.of(2024, 12, 4, 10, 8);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("문자열을 Date로 변환할 수 있다.")
    void successParseStringToDate() {
        LocalDate actual = parseStringToDate("2024-12-04");
        LocalDate expected = LocalDate.of(2024, 12, 4);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("문자열을 Time으로 변환할 수 있다.")
    void successParseStringToTime() {
        LocalTime actual = parseStringToTime("10:08");
        LocalTime expected = LocalTime.of(10, 8);
        assertThat(actual).isEqualTo(expected);
    }
}