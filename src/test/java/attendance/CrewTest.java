package attendance;

import attendance.domain.Attendance;
import attendance.domain.Crew;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CrewTest {

    @Test
    void 이미_출석한_경우_예외_반환() {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 16, 11, 00));
        Crew crew = new Crew("훌라", List.of(attendance));

        assertThatThrownBy(() -> crew.existInAttendances(LocalDate.of(2024,12,16)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이미_출석하지않은_경우_예외_반환X() {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 16, 11, 00));
        Crew crew = new Crew("훌라", List.of(attendance));

        assertThatCode(() -> crew.existInAttendances(LocalDate.of(2024,12,17))).doesNotThrowAnyException();
    }
}
