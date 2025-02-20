package global.util;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.as;
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

    @Test
    void getDateByInputDay가_해당월_해당날짜의_LocalDate를_반환한다() {
        LocalDate dateByInputDay = DateUtil.getDateByInputDay(3);
        LocalDate targetDate = LocalDate.of(2024, 12, 3);
        assertThat(dateByInputDay).isEqualTo(targetDate);
    }

}