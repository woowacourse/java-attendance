package domain.constants;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class HolidayTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("크리스마스를 올바르게 비교한다.")
        @Test
        public void isChristmas() throws Exception {
            // given
            final LocalDate christmas = LocalDate.of(2025, 12, 25);

            // when
            final boolean actual = Holiday.isHoliday(christmas);

            // then
            assertThat(actual).isTrue();
        }

    }
}
