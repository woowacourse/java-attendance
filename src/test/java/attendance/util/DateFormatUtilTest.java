package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateFormatUtilTest {


    @DisplayName("주어진 일(day) 문자열을 파싱하면 2024년 12월의 해당 날짜의 LocalDate가 반환된다")
    @Test
    void test_parseDate() {
        // given
        String day = "15";

        // when
        LocalDate result = DateUtil.parseDate(day);

        // then
        assertThat(result).isEqualTo(LocalDate.of(2024, 12, 15));
    }

    @DisplayName("월요일인 경우 주말이 아니다")
    @Test
    void isWeekend_withMonday_shouldReturnFalse() {
        // given
        LocalDate monday = LocalDate.of(2024, 12, 2);

        // when
        boolean result = DateUtil.isWeekend(monday);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("토요일인 경우 주말이 맞다")
    @Test
    void isWeekend_withSaturday_shouldReturnTrue() {
        // given
        LocalDate saturday = LocalDate.of(2024, 12, 1);

        // when
        boolean result = DateUtil.isWeekend(saturday);

        // then
        assertThat(result).isTrue();
    }

}