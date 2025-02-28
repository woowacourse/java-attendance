package domain.attendance;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {
    @DisplayName("특정 날짜가 공휴일인지 판단한다")
    @Test
    void test() {
        // given
        LocalDateTime christmas = LocalDateTime.of(2024, 12, 25, 10, 0);
        // when
        boolean isHoliday = Holiday.isHoliday(christmas.toLocalDate());

        // then
        Assertions.assertThat(isHoliday).isTrue();
    }

}