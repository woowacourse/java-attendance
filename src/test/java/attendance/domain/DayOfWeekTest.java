package attendance.domain;

import static attendance.domain.DayOfWeek.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DayOfWeekTest {

    private static final int ABSENCE_TIME = 31;

    @DisplayName("월요일 1시 5분에 출석할 경우, 지각시간은 5분이다.")
    @Test
    void late_time_calculate_on_monday() {
        LocalTime attendanceTime = LocalTime.of(13, 5);
        int lateTime = MONDAY.calculateLateTime(attendanceTime);
        assertThat(lateTime).isEqualTo(attendanceTime.getMinute());
    }

    @DisplayName("화요일 10시 5분에 출석할 경우, 지각시간은 5분이다.")
    @Test
    void late_time_calculate_tuesday() {
        LocalTime attendanceTime = LocalTime.of(10, 5);
        int lateTime = TUESDAY.calculateLateTime(attendanceTime);
        assertThat(lateTime).isEqualTo(attendanceTime.getMinute());
    }

    @DisplayName("화요일 11시 0분에 출석할 경우, ABSENCE_TIME이 반환된다.")
    @Test
    void absent_time_calculate_tuesday() {
        LocalTime attendanceTime = LocalTime.of(11, 0);
        int lateTime = TUESDAY.calculateLateTime(attendanceTime);
        assertThat(lateTime).isEqualTo(ABSENCE_TIME);
    }

    @DisplayName("2025년 2월 21일이 주어졌을 때, 금요일이 반환되어야 한다.")
    @Test
    void given_2025_2_21_then_return_FRIDAY() {
        LocalDate localDate = LocalDate.of(2025, 2, 21);
        DayOfWeek dayOfWeek = calculateDayOfWeek(localDate);
        assertThat(dayOfWeek).isEqualTo(FRIDAY);
    }

    @DisplayName("2025년 2월 22일이 주어졌을 때, 토요일이 반환되어야 한다.")
    @Test
    void given_2025_2_22_then_return_WEEKEND() {
        LocalDate localDate = LocalDate.of(2025, 2, 22);
        DayOfWeek dayOfWeek = calculateDayOfWeek(localDate);
        assertThat(dayOfWeek).isEqualTo(SATURDAY);
    }
}
