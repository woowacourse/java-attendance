package attendance.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeFormatterTest {

    @Test
    @DisplayName("날짜에 대한 포맷 메세지를 생성한다 ")
    void 날짜에_대한_포맷_메세지를_생성한다_() {
        // Given
        LocalDate date = LocalDate.of(2024, 12, 3);

        // When & Then
        assertThat(TimeFormatter.makeDateMessage(date)).isEqualTo("12월 03일 화요일");
    }

    @Test
    @DisplayName("시간에 대한 포맷 메세지를 생성한다 ")
    void 시간에_대한_포맷_메세지를_생성한다_() {
        // Given
        LocalTime time = LocalTime.of(10, 0);

        // When & Then
        assertThat(TimeFormatter.makeTimeMessage(time)).isEqualTo("10:00");
    }

    @Test
    @DisplayName("날짜와 시간에 대한 포맷 메세지를 생성한다 ")
    void 날짜와_시간에_대한_포맷_메세지를_생성한다_() {
        // Given
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, 10, 0);

        // When & Then
        assertThat(TimeFormatter.makeDateTimeMessage(time)).isEqualTo("12월 03일 화요일 10:00");
    }
}
