package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HolidayTest {

    @Test
    @DisplayName("공휴일 여부 확인 - 크리스마스")
    void isHoliday_Christmas() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 25);

        // when
        boolean result = Holiday.isHoliday(date);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("주말 여부 확인 - 토요일")
    void isHoliday_Saturday() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 14);

        // when
        boolean result = Holiday.isHoliday(date);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("주말 여부 확인 - 일요일")
    void isHoliday_Sunday() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 15);

        // when
        boolean result = Holiday.isHoliday(date);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("평일 여부 확인 - 등교일")
    void isHoliday_Weekday() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 16);

        // when
        boolean result = Holiday.isHoliday(date);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("공휴일 예외 처리 테스트")
    void validateHoliday_Exception() {
        // given
        LocalDateTime holidayTime = LocalDateTime.of(2024, 12, 25, 10, 0);

        // when & then
        assertThatThrownBy(() -> Holiday.validate(holidayTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다");
    }

    @Test
    @DisplayName("주말 예외 처리 테스트")
    void validateWeekend_Exception() {
        // given
        LocalDateTime weekendTime = LocalDateTime.of(2024, 12, 14, 10, 0);

        // when & then
        assertThatThrownBy(() -> Holiday.validate(weekendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다");
    }
}
