package domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.Attendance;
import attendance.domain.CrewAttendances;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CrewAttendancesTest {

    @Test
    void 생성시_출석중_특정_크루의_출석이_아니라면_예외가_발생한다() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance("pobi", LocalDateTime.of(2024, 12, 3, 10, 1)),
                new Attendance("neo", LocalDateTime.of(2024, 12, 3, 10, 1))
        );

        //when & then
        assertThatThrownBy(() -> new CrewAttendances("pobi", attendances))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("pobi의 출석만 이용하여 생성가능합니다.");
    }
}
