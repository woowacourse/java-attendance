package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimeTest {

    @Test
    @DisplayName("지각이면 true를 반환한다.")
    void test1() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime1 = LocalTime.of(10, 6);
        LocalTime localTime2 = LocalTime.of(10, 30);
        int beLateTime = 5;
        int absenceTime = 30;

        // when
        boolean result1 = AttendanceTime.isLate(localDate, localTime1, beLateTime, absenceTime);
        boolean result2 = AttendanceTime.isLate(localDate, localTime2, beLateTime, absenceTime);

        // then
        assertAll(
                () -> assertThat(result1).isTrue(),
                () -> assertThat(result2).isTrue()
        );
    }

    @Test
    @DisplayName("지각이 아니면 false를 반환한다.")
    void test2() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime1 = LocalTime.of(10, 5);
        LocalTime localTime2 = LocalTime.of(10, 31);
        int beLateTime = 5;
        int absenceTime = 30;

        // when
        boolean result1 = AttendanceTime.isLate(localDate, localTime1, beLateTime, absenceTime);
        boolean result2 = AttendanceTime.isLate(localDate, localTime2, beLateTime, absenceTime);

        // then
        Assertions.assertAll(
                () -> assertThat(result1).isFalse(),
                () -> assertThat(result2).isFalse()
        );
    }

    @Test
    @DisplayName("결석이면 true를 반환한다.")
    void test3() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 31);
        int absenceTime = 30;

        // when
        boolean result = AttendanceTime.isAbsence(localDate, localTime, absenceTime);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("결석이 아니면 false를 반환한다.")
    void test4() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime1 = LocalTime.of(10, 0);
        LocalTime localTime2 = LocalTime.of(10, 30);
        int absenceTime = 30;

        // when
        boolean result1 = AttendanceTime.isAbsence(localDate, localTime1, absenceTime);
        boolean result2 = AttendanceTime.isAbsence(localDate, localTime2, absenceTime);

        // then
        Assertions.assertAll(
                () -> assertThat(result1).isFalse(),
                () -> assertThat(result2).isFalse()
        );
    }

    @Test
    @DisplayName("운영 시간이면 true를 반환한다.")
    void test5() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 0);

        // when
        boolean result = AttendanceTime.isInOperationTime(localDate, localTime);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("운영 시간이 아니면 false를 반환한다.")
    void test6() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(7, 0);

        // when
        boolean result = AttendanceTime.isInOperationTime(localDate, localTime);

        assertThat(result).isFalse();
    }
}
