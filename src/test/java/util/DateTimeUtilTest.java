package util;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeUtilTest {
    @DisplayName("LocalDateTime을 날짜와 시간으로 변환한다")
    @Test
    void test() {
        // given & when
        String koreanLocalDateTime = DateTimeUtil.convertLocalDateTimeToString(LocalDateTime.of(2024, 12, 2, 10, 0));

        // then
        assertThat(koreanLocalDateTime).isEqualTo("12월 02일 월요일 10시 00분");
    }

    @DisplayName("LocalDate을 한국어 날짜로 변환한다")
    @Test
    void test2() {
        // given & when
        String koreanLocalDateTime = DateTimeUtil.convertLocalDateToString(LocalDate.of(2024, 12, 2));

        // then
        assertThat(koreanLocalDateTime).isEqualTo("12월 02일 월요일");
    }
}
