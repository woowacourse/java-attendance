package domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class HolidayTest {

    @Test
    void 해당_날짜가_공휴일인지_확인한다() {
        LocalDate date = LocalDate.of(2025, 2, 11);

        boolean isHoliday = Holiday.check(date);

        assertThat(isHoliday).isEqualTo(true);
    }

    @Test
    void 해당_날짜가_공휴일이_아님을_확인한다() {
        LocalDate date = LocalDate.of(2025, 2, 15);

        boolean isHoliday = Holiday.check(date);

        assertThat(isHoliday).isEqualTo(false);
    }

}
