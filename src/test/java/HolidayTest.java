import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HolidayTest {

    @Test
    @DisplayName("주어진 날짜가 공휴일인지 판정하는 기능")
    void checkDateIsHoliday() {
        //given
        HolidayChecker holidayChecker = new HolidayChecker();
        LocalDate christmas = LocalDate.of(2024, 12, 25);
        LocalDate notHoliday = LocalDate.of(2024, 12, 1);

        //when
        boolean actual = holidayChecker.isHoliday(christmas);
        boolean actual2 = holidayChecker.isHoliday(notHoliday);

        //then
        assertThat(actual).isEqualTo(true);
        assertThat(actual2).isEqualTo(false);
    }
}
