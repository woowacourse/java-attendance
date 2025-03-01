package view;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomDateTimeFormatterTest {
    @Test
    @DisplayName("날짜를 MM월 dd일 형태로 반환")
    void dateFormatTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 2);

        // when
        String formatted = CustomDateTimeFormatter.dateToString(date);

        // then
        assertThat(formatted).isEqualTo("12월 02일");
    }

    @Test
    @DisplayName("날짜를 한국어 요일로 변환")
    void dauOfWeekFormatTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 2);

        // when
        String formatted = CustomDateTimeFormatter.dateToDayOfWeek(date);

        // then
        assertThat(formatted).isEqualTo("월요일");
    }

    @Test
    @DisplayName("시간을 HH:mm 형태로 변환")
    void timeFormatTest() {
        // given
        LocalTime time = LocalTime.of(13, 1);

        // when
        String formatted = CustomDateTimeFormatter.timeToString(time);

        // then
        assertThat(formatted).isEqualTo("13:01");
    }
}