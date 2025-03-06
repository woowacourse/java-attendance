package domain.policy.attend.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceDatePolicyTest {

    private final AttendanceDatePolicy attendanceDatePolicy = new AttendanceDatePolicy();

    @Test
    @DisplayName("공휴일은 isHoliday에서 true를 반환한다 (LocalDate)")
    void holidayReturnTrue() {
        // given
        LocalDate christmas = LocalDate.of(2080, 12, 25);

        // when
        boolean isHoliday = attendanceDatePolicy.isHoliday(christmas);

        // then
        assertThat(isHoliday).isTrue();
    }

    @Test
    @DisplayName("공휴일이 아닌 날은 isHoliday에서 false를 반환한다 (LocalDate)")
    void nonHolidayReturnFalse() {
        // given
        LocalDate myBirthday = LocalDate.of(1998, 6, 8);

        // when
        boolean isHoliday = attendanceDatePolicy.isHoliday(myBirthday);

        // then
        assertThat(isHoliday).isFalse();
    }

    @Test
    @DisplayName("특별한 요일은 isSpecialDayOfWeek에서 true를 반환한다 (LocalDate)")
    void specialDayReturnTrue() {
        // given
        LocalDate specialDay = LocalDate.of(2024, 12, 9);

        // when
        boolean isSpecialDayOfWeek = attendanceDatePolicy.isSpecialDay(specialDay);

        // then
        assertAll(
                () -> assertThat(specialDay.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY),
                () -> assertThat(isSpecialDayOfWeek).isTrue()
        );
    }

    @Test
    @DisplayName("특별하지 않은 요일은 isSpecialDayOfWeek에서 false를 반환한다 (LocalDate)")
    void nonSpecialDayReturnFalse() {
        // given
        LocalDate specialDay = LocalDate.of(2024, 12, 11);

        // when
        boolean isSpecialDayOfWeek = attendanceDatePolicy.isSpecialDay(specialDay);

        // then
        assertAll(
                () -> assertThat(specialDay.getDayOfWeek()).isEqualTo(DayOfWeek.WEDNESDAY),
                () -> assertThat(isSpecialDayOfWeek).isFalse()
        );
    }

    @Test
    @DisplayName("토, 일요일은 isWeekend에서 true를 반환한다 (LocalDate)")
    void weekendReturnTrue() {
        // given
        LocalDate saturday = LocalDate.of(2024, 12, 14);
        LocalDate sunday = LocalDate.of(2024, 12, 15);

        // when
        boolean isSaturdayWeekend = attendanceDatePolicy.isWeekend(saturday);
        boolean isSundayWeekend = attendanceDatePolicy.isWeekend(sunday);

        // then
        assertAll(
                () -> assertThat(saturday.getDayOfWeek()).isEqualTo(DayOfWeek.SATURDAY),
                () -> assertThat(sunday.getDayOfWeek()).isEqualTo(DayOfWeek.SUNDAY),
                () -> assertThat(isSaturdayWeekend).isTrue(),
                () -> assertThat(isSundayWeekend).isTrue()
        );
    }

    @Test
    @DisplayName("월, 화, 수, 목, 금요일은 isWeekend에서 true를 반환한다 (LocalDate)")
    void weekdayReturnTrue() {
        // given
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDate tuesday = LocalDate.of(2024, 12, 10);
        LocalDate wednesday = LocalDate.of(2024, 12, 11);
        LocalDate thursday = LocalDate.of(2024, 12, 12);
        LocalDate friday = LocalDate.of(2024, 12, 13);

        // when
        boolean isMondayWeekend = attendanceDatePolicy.isWeekend(monday);
        boolean isTuesdayWeekend = attendanceDatePolicy.isWeekend(tuesday);
        boolean isWednesdayWeekend = attendanceDatePolicy.isWeekend(wednesday);
        boolean isThursdayWeekend = attendanceDatePolicy.isWeekend(thursday);
        boolean isFridayWeekend = attendanceDatePolicy.isWeekend(friday);

        // then
        assertAll(
                () -> assertThat(monday.getDayOfWeek()).isEqualTo(DayOfWeek.MONDAY),
                () -> assertThat(tuesday.getDayOfWeek()).isEqualTo(DayOfWeek.TUESDAY),
                () -> assertThat(wednesday.getDayOfWeek()).isEqualTo(DayOfWeek.WEDNESDAY),
                () -> assertThat(thursday.getDayOfWeek()).isEqualTo(DayOfWeek.THURSDAY),
                () -> assertThat(friday.getDayOfWeek()).isEqualTo(DayOfWeek.FRIDAY),

                () -> assertThat(isMondayWeekend).isFalse(),
                () -> assertThat(isTuesdayWeekend).isFalse(),
                () -> assertThat(isWednesdayWeekend).isFalse(),
                () -> assertThat(isThursdayWeekend).isFalse(),
                () -> assertThat(isFridayWeekend).isFalse()
        );
    }
}
