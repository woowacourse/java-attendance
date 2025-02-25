package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @Test
    void 해당_날짜_기록이_존재하면_true를_반환한다() {
        Attendances attendances = new Attendances(List.of(LocalDateTime.of(2024, 12, 13, 9, 59)));
        LocalDate date = LocalDate.of(2024, 12, 13);
        final var result = attendances.existsByDate(date);

        assertThat(result).isTrue();
    }

    @Test
    void 해당_날짜_기록이_존재하면_true를_반환한다2() {
        Attendances attendances = new Attendances(List.of(LocalDateTime.of(2024, 12, 12, 9, 59)));
        LocalDate date = LocalDate.of(2024, 12, 12);
        final var result = attendances.existsByDate(date);

        assertThat(result).isTrue();
    }

    @Test
    void 해당_날짜_기록이_존재하지_않으면_false를_반환한다() {
        Attendances attendances = new Attendances(List.of(LocalDateTime.of(2024, 12, 13, 9, 59)));
        LocalDate date = LocalDate.of(2024, 12, 12);
        final var result = attendances.existsByDate(date);

        assertThat(result).isFalse();
    }
}
