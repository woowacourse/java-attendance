package attendance.controller.validator;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class HolidayValidatorTest {

    @Test
    void 공휴일이면_예외() {
        LocalDate date = LocalDate.of(2024, 12, 25);    // 크리스마스
        assertThatThrownBy(() -> HolidayValidator.validate(date))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주말이면_예외() {
        LocalDate date = LocalDate.of(2024, 12, 14);    // 토요일
        assertThatThrownBy(() -> HolidayValidator.validate(date))
                .isInstanceOf(IllegalArgumentException.class);
    }
}