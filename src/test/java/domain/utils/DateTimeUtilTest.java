package domain.utils;

import global.utils.DateTimeUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilTest {

    @Test
    void 이번달_시작일과_오늘의_사이인_날짜인지_확인한다() {
        LocalDate trueDate = LocalDate.of(2024, 12, 10);
        LocalDate falseDate = LocalDate.of(2024, 12, 18);
        LocalDate beforeMonthDate = LocalDate.of(2024, 11, 30);
        LocalDate thresholdStartDate = LocalDate.of(2024, 12, 1);
        LocalDate thresholdEnbDate = LocalDate.of(2024, 12, 14);

        assertThat(DateTimeUtil.isDateInAvailableAttendance(trueDate)).isTrue();
        assertThat(DateTimeUtil.isDateInAvailableAttendance(falseDate)).isFalse();
        assertThat(DateTimeUtil.isDateInAvailableAttendance(beforeMonthDate)).isFalse();
        assertThat(DateTimeUtil.isDateInAvailableAttendance(thresholdStartDate)).isTrue();
        assertThat(DateTimeUtil.isDateInAvailableAttendance(thresholdEnbDate)).isTrue();
    }
}