package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeTest {

    @Test
    @DisplayName("출석 타입을 반환한다.")
    void test1() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(9, 30);

        // when
        AttendanceType result = AttendanceType.calculate(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.SUCCESS);
    }

    @Test
    @DisplayName("지각 타입을 반환한다.")
    void test2() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 6);

        // when
        AttendanceType result = AttendanceType.calculate(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.BE_LATE);
    }

    @Test
    @DisplayName("결석 타입을 반환한다.")
    void test3() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 31);

        // when
        AttendanceType result = AttendanceType.calculate(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.ABSENCE);
    }
}
