package model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AttendanceDateTest {

    @Test
    void 주말인_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceDate(LocalDate.of(2024, 12, 15)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공휴일인_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceDate(LocalDate.of(2024, 12, 25)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
