package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 주말이나_휴일이아니면_출석객체를_생성할수있다() {
        LocalTime time = LocalTime.of(13, 4);

        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDateTime localDateTime = LocalDateTime.of(monday, time);

        assertThatCode(() -> Attendance.of(localDateTime))
            .doesNotThrowAnyException();
    }

    @Test
    void 등교날짜가_주말이면_예외가_발생한다() {
        LocalDateTime saturday = LocalDateTime.of(2024, 12, 14, 10, 0);

        assertThatThrownBy(() -> Attendance.of(saturday))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 등교날짜가_공휴일이면_예외가_발생한다() {
        LocalDateTime thursday_christmas = LocalDateTime.of(2025, 12, 25, 10, 0);

        assertThatThrownBy(() -> Attendance.of(thursday_christmas))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
