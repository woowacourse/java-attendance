package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HolidayTest {

    @Test
    @DisplayName("공휴일이면 해당 공휴일에 맞는 enum을 반환한다")
    void fromTest() {
        assertThat(Holiday.from(LocalDate.of(2025, 12, 25))).isEqualTo(Holiday.CHRISTMAS);
        assertThat(Holiday.from(LocalDate.of(2025, 12, 26))).isEqualTo(Holiday.NONE);
    }
}
