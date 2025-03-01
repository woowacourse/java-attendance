package model;

import attendance.model.AttendanceDate;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    @Test
    void 공휴일을_출석_날짜로_설정하면_예외가_발생한다() {
        // given

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceDate(2024, 12, 25))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 토요일을_출석_날짜로_설정하면_예외가_발생한다() {
        // given

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceDate(2024, 12, 14))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 일요일을_출석_날짜로_설정하면_예외가_발생한다() {
        // given

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceDate(2024, 12, 15))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void LocalDate를_생성자로_일요일을_출석_날짜로_설정하면_예외가_발생한다() {
        // given
        LocalDate now = LocalDate.of(2024, 12, 15);

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceDate(now))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 시스템_운영기간이_아닐_경우_예외가_발생한다() {
        // given
        LocalDate now = LocalDate.of(2025, 1, 1);

        // when & then
        Assertions.assertThatThrownBy(() -> new AttendanceDate(now))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("시스템 운영 기간은");
    }
}
