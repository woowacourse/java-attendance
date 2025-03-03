package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CustomDayOfWeekTest {

    @Test
    void 날짜에_따라_알맞은_요일을_반환한다() {
        LocalDate monday = LocalDate.of(2025, 2, 24);
        LocalDate thursday = LocalDate.of(2025, 2, 27);
        LocalDate saturday = LocalDate.of(2025, 3, 1);

        assertThat(CustomDayOfWeek.MONDAY).isEqualTo(CustomDayOfWeek.getInstance(monday));
        assertThat(CustomDayOfWeek.THURSDAY).isEqualTo(CustomDayOfWeek.getInstance(thursday));
        assertThat(CustomDayOfWeek.SATURDAY).isEqualTo(CustomDayOfWeek.getInstance(saturday));

    }

}