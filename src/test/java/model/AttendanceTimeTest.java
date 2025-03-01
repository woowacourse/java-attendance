package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimeTest {

    @Test
    @DisplayName("지각이면 true를 반환한다.")
    void test1() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 6);
        int beLateTime = 5;
        int absenceTime = 30;

        // when
        boolean result = AttendanceTime.isLate(localDate, localTime, beLateTime, absenceTime);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("지각이 아니면 false를 반환한다.")
    void test2() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime1 = LocalTime.of(10, 4);
        LocalTime localTime2 = LocalTime.of(10, 31);
        int beLateTime = 5;
        int absenceTime = 30;

        // when
        boolean result1 = AttendanceTime.isLate(localDate, localTime1, beLateTime, absenceTime);
        boolean result2 = AttendanceTime.isLate(localDate, localTime2, beLateTime, absenceTime);

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
    }
}
