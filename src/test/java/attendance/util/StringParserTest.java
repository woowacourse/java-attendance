package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringParserTest {

    @DisplayName("시간 문자열을 LocalTime으로 변환한다")
    @Test
    void parseLocalTimeTest() {
        // Given

        // When
        LocalTime parsedLocalTime = StringParser.parseLocalTime("11:11");

        // Then
        assertThat(parsedLocalTime).hasHour(11).hasMinute(11);
    }

    @DisplayName("시간 형식이 HH:MM가 아니라면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"11%12", "25:12"})
    void invalidLocalTimeFormatTest(String input) {
        // Given

        // When & Then
        assertThatThrownBy(() -> StringParser.parseLocalTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 시간이 HH:MM 형식에 맞지 않습니다.");
    }

    @DisplayName("정수 문자열을 정수로 변환한다")
    @Test
    void parseIntTest() {
        // Given
        final String input = "11";

        // When
        int parsedInt = StringParser.parseInt(input);

        // Then
        assertThat(parsedInt).isEqualTo(11);
    }

    @DisplayName("정수 문자열이 아니라면 예외가 발생한다")
    @Test
    void invalidIntFormatTest() {
        // Given
        final String input = "11일";

        // When & Then
        Assertions.assertThatThrownBy(() -> StringParser.parseInt(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 정수 문자열이여야합니다.");
    }

    @DisplayName("12월 일자 문자열로 받아서 LocalDate로 변환한다")
    @Test
    void parseLocalDateTest() {
        // Given
        final int year = 2024;
        final int month = 12;
        final String input = "11";

        // When
        LocalDate localDate = StringParser.parseLocalDate(year, month, input);

        // Then
        assertThat(localDate).isEqualTo(LocalDate.of(2024, 12, 11));
    }

    @DisplayName("유효하지 않은 12월 일자 문자열이라면 예외가 발생한다")
    @Test
    void invalidLocalDateFormatTest() {
        // Given
        final int year = 2024;
        final int month = 12;
        final String input = "40";

        // When & Then
        Assertions.assertThatThrownBy(() -> StringParser.parseLocalDate(year, month, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효한 일자이여야합니다.");
    }

    @DisplayName("날짜와 시간 문자열을 받아서 LocalDateTime으로 변환한다")
    @Test
    void parseLocalDateTime() {
        // Given
        final String input = "2024-12-03 10:06";

        // When
        LocalDateTime localDateTime = StringParser.parseLocalDateTime(input);

        // Then
        assertThat(localDateTime).isEqualTo(LocalDateTime.of(2024, 12, 3, 10, 6));
    }

    @DisplayName("유효하지 않은 날짜와 시간 문자열이라면 예외가 발생한다")
    @Test
    void invalidLocalDateParseTest() {
        // Given
        final String input = "20241203T10:06";

        // When & Then
        Assertions.assertThatThrownBy(() -> StringParser.parseLocalDateTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 날짜 및 시간이 yyyy-MM-dd HH:mm 형식에 맞지 않습니다.");
    }

}
