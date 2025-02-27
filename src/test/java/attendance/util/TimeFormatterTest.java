package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.util.TimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeFormatterTest {

    @DisplayName("LocalDateTime을 출력 형식으로 변환한다")
    @Test
    void localDateTimeFormatTest() {
        // Given
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 9, 55);

        // When & Then
        assertThat(TimeFormatter.formatDateTime(localDateTime)).isEqualTo("12월 03일 화요일 09:55");
    }

    @DisplayName("LocalDate를 출력 형식으로 변환한다")
    @Test
    void localDateFormatTest() {
        // Given
        LocalDate localDate = LocalDate.of(2024, 12, 3);

        // When & Then
        assertThat(TimeFormatter.formatDate(localDate)).isEqualTo("12월 03일 화요일");
    }

    @DisplayName("LocalTime을 출력 형식으로 변환한다")
    @Test
    void localTimeFormatTest() {
        // Given
        LocalTime localTime = LocalTime.of(9, 50);

        // When & Then
        assertThat(TimeFormatter.formatTime(localTime)).isEqualTo("09:50");
    }
}
