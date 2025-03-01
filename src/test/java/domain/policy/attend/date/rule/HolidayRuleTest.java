package domain.policy.attend.date.rule;

import domain.policy.attend.date.HolidayRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.MonthDay;

import static org.assertj.core.api.Assertions.assertThat;

class HolidayRuleTest {

    @Test
    @DisplayName("공휴일은 isHoliday에서 true를 반환한다 (MonthDay)")
    void holidayReturnTrue() {
        // given
        MonthDay christmas = MonthDay.of(12, 25);

        // when
        boolean isHoliday = HolidayRule.isHoliday(christmas);

        // then
        assertThat(isHoliday).isTrue();
    }

    @Test
    @DisplayName("공휴일이 아닌 날은 isHoliday에서 false를 반환한다 (MonthDay)")
    void nonHolidayReturnFalse() {
        // given
        MonthDay myBirthday = MonthDay.of(6, 8);

        // when
        boolean isHoliday = HolidayRule.isHoliday(myBirthday);

        // then
        assertThat(isHoliday).isFalse();
    }
}
