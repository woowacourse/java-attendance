package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeTest {

    @Test
    @DisplayName("금요일 출석 타입을 반환한다.")
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
    @DisplayName("금요일 지각 타입을 반환한다.")
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
    @DisplayName("금요일 결석 타입을 반환한다.")
    void test3() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 31);

        // when
        AttendanceType result = AttendanceType.calculate(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.ABSENCE);
    }

    @Test
    @DisplayName("월요일 출석 타입을 반환한다.")
    void test4() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 24);
        LocalTime localTime = LocalTime.of(12, 30);

        // when
        AttendanceType result = AttendanceType.calculate(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.SUCCESS);
    }

    @Test
    @DisplayName("월요일 지각 타입을 반환한다.")
    void test5() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 24);
        LocalTime localTime = LocalTime.of(13, 6);

        // when
        AttendanceType result = AttendanceType.calculate(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.BE_LATE);
    }

    @Test
    @DisplayName("월요일 결석 타입을 반환한다.")
    void test6() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 24);
        LocalTime localTime = LocalTime.of(13, 31);

        // when
        AttendanceType result = AttendanceType.calculate(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.ABSENCE);
    }
}
