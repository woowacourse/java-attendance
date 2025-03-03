package constant;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HolidayTest {

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-25", "2024-12-28", "2024-12-29"})
    @DisplayName("공휴일 확인 로직 테스트")
    void isHoliday(String date) {
        // given
        LocalDate localDate = LocalDate.parse(date);
        // when
        boolean isHoliday = Holiday.isHoliday(localDate);
        // then
        assertThat(isHoliday).isTrue();
    }
}