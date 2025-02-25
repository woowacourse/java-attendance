package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    @Test
    void 주말인_경우_예외가_발생한다() {
        // given
        LocalDate weekendDate = LocalDate.of(2024, 12, 15); // 일요일

        // when & then
        assertThatThrownBy(() -> new AttendanceDate(weekendDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공휴일인_경우_예외가_발생한다() {
        // given
        LocalDate holidayDate = LocalDate.of(2024, 12, 25); // 크리스마스

        // when & then
        assertThatThrownBy(() -> new AttendanceDate(holidayDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
