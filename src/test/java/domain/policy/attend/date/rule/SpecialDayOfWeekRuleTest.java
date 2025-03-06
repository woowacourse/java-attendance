package domain.policy.attend.date.rule;

import domain.policy.attend.date.SpecialDayOfWeekRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialDayOfWeekRuleTest {

    @Test
    @DisplayName("특별한 요일은 isSpecialDayOfWeek에서 true를 반환한다 (DayOfWeek)")
    void specialDayReturnTrue() {
        // given
        DayOfWeek specialDayOfWeek = DayOfWeek.MONDAY;

        // when
        boolean isSpecialDayOfWeek = SpecialDayOfWeekRule.isSpecialDayOfWeek(specialDayOfWeek);

        // then
        assertThat(isSpecialDayOfWeek).isTrue();
    }

    @Test
    @DisplayName("특별하지 않은 요일은 isSpecialDayOfWeek에서 false를 반환한다 (DayOfWeek)")
    void nonSpecialDayReturnFalse() {
        // given
        DayOfWeek nonSpecialDayOfWeek = DayOfWeek.WEDNESDAY;

        // when
        boolean isSpecialDayOfWeek = SpecialDayOfWeekRule.isSpecialDayOfWeek(nonSpecialDayOfWeek);

        // then
        assertThat(isSpecialDayOfWeek).isFalse();
    }
}
