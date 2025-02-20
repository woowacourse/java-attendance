package global.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilTest {

    @Test
    void 주말은_false를_return한다() {
        assertThat(DateUtil.isWeekday(LocalDate.of(2024, 12, 7))).isEqualTo(false);
    }

    @Test
    void 평일은_true를_return한다() {
        assertThat(DateUtil.isWeekday(LocalDate.of(2024, 12, 5))).isEqualTo(true);
    }

    @Test
    void 공휴일은_false를_return한다() {
        assertThat(DateUtil.isWeekday(LocalDate.of(2024, 12, 25))).isEqualTo(false);
    }

    @Test
    void getFirstDayOfMonth_메서드가_첫날을_반환한다() {
        assertThat(DateUtil.getFirstDateOfMonth()).isEqualTo(LocalDate.of(2024, 12, 1));
    }

}