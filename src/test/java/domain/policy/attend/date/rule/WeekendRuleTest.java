package domain.policy.attend.date.rule;

import domain.policy.attend.date.WeekendRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class WeekendRuleTest {

    @Test
    @DisplayName("토, 일요일은 isWeekend에서 true를 반환한다 (DayOfWeek)")
    void weekendReturnTrue() {
        // given
        DayOfWeek saturday = DayOfWeek.SATURDAY;
        DayOfWeek sunday = DayOfWeek.SUNDAY;

        // when
        boolean isSaturdayWeekend = WeekendRule.isWeekend(saturday);
        boolean isSundayWeekend = WeekendRule.isWeekend(sunday);

        // then
        assertAll(
                () -> assertThat(isSaturdayWeekend).isTrue(),
                () -> assertThat(isSundayWeekend).isTrue()
        );
    }

    @Test
    @DisplayName("월, 화, 수, 목, 금요일은 isWeekend에서 true를 반환한다 (DayOfWeek)")
    void weekdayReturnTrue() {
        // given
        DayOfWeek monday = DayOfWeek.MONDAY;
        DayOfWeek tuesday = DayOfWeek.TUESDAY;
        DayOfWeek wednesday = DayOfWeek.WEDNESDAY;
        DayOfWeek thursday = DayOfWeek.THURSDAY;
        DayOfWeek friday = DayOfWeek.FRIDAY;

        // when
        boolean isMondayWeekend = WeekendRule.isWeekend(monday);
        boolean isTuesdayWeekend = WeekendRule.isWeekend(tuesday);
        boolean isWednesdayWeekend = WeekendRule.isWeekend(wednesday);
        boolean isThursdayWeekend = WeekendRule.isWeekend(thursday);
        boolean isFridayWeekend = WeekendRule.isWeekend(friday);

        // then
        assertAll(
                () -> assertThat(isMondayWeekend).isFalse(),
                () -> assertThat(isTuesdayWeekend).isFalse(),
                () -> assertThat(isWednesdayWeekend).isFalse(),
                () -> assertThat(isThursdayWeekend).isFalse(),
                () -> assertThat(isFridayWeekend).isFalse()
        );
    }
}
