package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class AttendanceDateTest {

    @Test
    void 토요일이면_예외를_던진다() {
        LocalDate saturday = LocalDate.of(2024, 12, 14);
        assertThatThrownBy(() -> new AttendanceDate(saturday))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 일요일이면_예외를_던진다() {
        LocalDate sunday = LocalDate.of(2024, 12, 15);
        assertThatThrownBy(() -> new AttendanceDate(sunday))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크리스마스면_예외를_던진다() {
        LocalDate christmas = LocalDate.of(2024, 12, 25);
        assertThatThrownBy(() -> new AttendanceDate(christmas))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 평일이면_예외를_던지지_않는다() {
        LocalDate friday = LocalDate.of(2024, 12, 13);
        assertThatCode(() -> new AttendanceDate(friday))
                .doesNotThrowAnyException();
    }
}
