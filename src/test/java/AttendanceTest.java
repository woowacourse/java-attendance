import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 닉네임과_등교시간으로_출석을_한다() {
        Crew crew = new Crew("이든");
        LocalTime time = LocalTime.of(9, 59);
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), time);

        assertThatCode(() -> Attendance.of(crew, localDateTime))
            .doesNotThrowAnyException();
    }

    @Test
    void 등교날짜가_주말이면_예외가_발생한다() {
        Crew crew = new Crew("이든");
        LocalTime time = LocalTime.of(9, 59);

        LocalDate saturday = LocalDate.of(2025, 2, 22);
        LocalDateTime localDateTime = LocalDateTime.of(saturday, time);

        assertThatThrownBy(() -> Attendance.of(crew, localDateTime))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
