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
        final int todayDayOfMonth = todayDateTime.getDateTime().getDayOfMonth();
        final int toDayOfMonth =  ValidManager.getInstance().getLastByDayOfMonth(todayDayOfMonth);

        // when
        final Set<Integer> datesTo = ValidManager.getInstance().getDatesTo(toDayOfMonth);

        // then
        datesTo.forEach(dayOfMonth -> Assertions.assertThat(dayOfMonth < todayDayOfMonth).isTrue());
    }

    @Test
    @DisplayName("입력 일자보다 유효한 출석일인 가장 최근 과거 날을 잘 찾아오는지")
    void getLastByDayOfMonth() {

        // given
        final AttendanceDateTime todayDateTime = AttendanceDateTime.of("2024-12-17 10:00");
        final int todayDayOfMonth = todayDateTime.getDateTime().getDayOfMonth();
        final int toDayOfMonth =  ValidManager.getInstance().getLastByDayOfMonth(todayDayOfMonth);

        // when

        // then
        Assertions.assertThat(toDayOfMonth).isEqualTo(16);
    }
}