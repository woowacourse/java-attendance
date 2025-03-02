package model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;


class ValidManagerTest {

    @Test
    @DisplayName("유효한 날짜만 가지고 있는 지")
    void validDates() {

        // given
        // when
        final ValidManager instance = ValidManager.getInstance();
        final Set<Integer> validDates = instance.getValidDates();

        // then
        for (final int day : validDates) {
            System.out.println(day);
        }
    }

    @Test
    @DisplayName("원하는 DayOfMonth까지만 가져올 수 있는 지")
    void getDatesTo() {

        // given
        final AttendanceDateTime todayDateTime = AttendanceDateTime.of("2024-12-16 10:00");
        final int today = todayDateTime.getDateTime().getDayOfMonth();

        // when
        final Set<Integer> datesTo = ValidManager.getInstance().getDatesTo(todayDateTime.getDateTime().getDayOfMonth());

        // then
        datesTo.forEach(dayOfMonth -> Assertions.assertThat(dayOfMonth < today).isTrue());
    }
}