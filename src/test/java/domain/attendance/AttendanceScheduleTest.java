package domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceScheduleTest {
    @Test
    @DisplayName("월요일 AttendanceTime 으로부터 지각한 시간 반환")
    void getLateMinuteFromAttendanceTimeInMondayTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 2);
        LocalTime time1 = LocalTime.of(13, 5);
        LocalTime time2 = LocalTime.of(9, 3);

        // when
        int lateMinutes1 = AttendanceSchedule.calculateLateMinutes(today, time1);
        int lateMinutes2 = AttendanceSchedule.calculateLateMinutes(today, time2);

        // then
        assertThat(lateMinutes1).isEqualTo(5);
        assertThat(lateMinutes2).isEqualTo(0);
    }

    @Test
    @DisplayName("월요일이 아닌 평일 AttendanceTime 으로부터 지각한 시간 반환")
    void getLateMinuteFromAttendanceTimeTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 3);
        LocalTime time1 = LocalTime.of(10, 5);
        LocalTime time2 = LocalTime.of(8, 3);

        // when
        int lateMinutes1 = AttendanceSchedule.calculateLateMinutes(today, time1);
        int lateMinutes2 = AttendanceSchedule.calculateLateMinutes(today, time2);

        // then
        assertThat(lateMinutes1).isEqualTo(5);
        assertThat(lateMinutes2).isEqualTo(0);
    }

    @Test
    @DisplayName("주말/공휴일 입력 시 예외 발생")
    void givenWeekendThrowException() {
        // given
        LocalDate weekend = LocalDate.of(2024, 12, 1);
        LocalDate holiday = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(10, 5);

        // when, then
        assertThatThrownBy(() -> AttendanceSchedule.calculateLateMinutes(weekend, time))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> AttendanceSchedule.calculateLateMinutes(holiday, time))
                .isInstanceOf(IllegalArgumentException.class);
    }
}