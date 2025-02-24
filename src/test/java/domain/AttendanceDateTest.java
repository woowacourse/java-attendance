package domain;

import domain.rule.AttendanceDateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceDateTest {

    @Test
    @DisplayName("유효한 평일 날짜로 AttendanceDate 객체를 생성할 수 있다")
    void whenValidWeekday() {
        // given
        LocalDate validDate = LocalDate.of(2024, 12, 13); // 금요일

        // when
        AttendanceDate attendanceDate = AttendanceDate.from(validDate);

        // then
        assertThat(attendanceDate).isNotNull();
        assertThat(attendanceDate.date()).isEqualTo(validDate);
    }

    @Test
    @DisplayName("등교가 늦는 특별한 날 여부를 확인할 수 있다 (월요일)")
    void whenSpecialDay() {
        // given
        LocalDate specialDate = LocalDate.of(2024, 12, 16); // 월요일
        AttendanceDate attendanceDate = AttendanceDate.from(specialDate);

        // when
        boolean isSpecial = attendanceDate.isSpecialDay();

        // then
        assertThat(isSpecial).isEqualTo(AttendanceDateRule.isSpecialDay(specialDate));
    }

    @Test
    @DisplayName("주말에는 출석할 수 없으며 예외를 던진다")
    void validateWeekend() {
        // given
        LocalDate weekendDate = LocalDate.of(2024, 12, 14); // 토요일

        // when
        // then
        assertThatThrownBy(() -> AttendanceDate.from(weekendDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }

    @Test
    @DisplayName("주말 여부를 검사할 수 있다")
    void checkWeekend() {
        // given
        DayOfWeek saturday = DayOfWeek.SATURDAY;
        DayOfWeek sunday = DayOfWeek.SUNDAY;
        DayOfWeek monday = DayOfWeek.MONDAY;

        // when
        // then
        assertAll(
                () -> assertThat(AttendanceDateRule.isWeekend(saturday)).isTrue(),
                () -> assertThat(AttendanceDateRule.isWeekend(sunday)).isTrue(),
                () -> assertThat(AttendanceDateRule.isWeekend(monday)).isFalse());
    }

    @Test
    @DisplayName("공휴일에는 출석할 수 없으며 예외를 던진다")
    void validateHoliday() {
        // given
        LocalDate holidayDate = LocalDate.of(2024, 12, 25);

        // when
        // then
        assertThatThrownBy(() -> AttendanceDate.from(holidayDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석할 수 없습니다.");
    }
}
