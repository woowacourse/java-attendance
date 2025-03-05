package util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceDateTimeFormatterTest {

    @DisplayName("LocalDate를 날짜 형식 문자열로 변환한다")
    @Test
    void formatLocalDateTest() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        assertThat(AttendanceDateTimeFormatter.formatLocalDate(date))
                .contains("12월 03일 화요일");
    }

    @DisplayName("LocalTime을 시간 형식 문자열로 변환한다")
    @Test
    void formatLocalTimeTest() {
        LocalTime time = LocalTime.of(12, 34);
        assertThat(AttendanceDateTimeFormatter.formatLocalTime(time))
                .contains("12:34");
    }
}
